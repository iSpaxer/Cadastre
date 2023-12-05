package TgBot.filter;

import TgBot.jwt.factory.DefaultTelegramTokenFactory;
import TgBot.jwt.model.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.time.Instant;
import java.util.function.Function;

public class TelegramTokenFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/getClients",HttpMethod.GET.name());
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    private Function<Update, Token> telegramTokenFactory = new DefaultTelegramTokenFactory();

    private Function<Token, String> telegramTokenSerializer = Object::toString;
    private Function<String, Token> telegramTokenDeserializer;

    public void telegramTokenDeserializer(Function<String, Token> telegramTokenDeserializer) {
        this.telegramTokenDeserializer = telegramTokenDeserializer;
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (this.requestMatcher.matches(request)) {
            var tjwt = request.getHeader("Authorization");
            if (tjwt != null) {
                tjwt = tjwt.substring(7);
                var token = telegramTokenDeserializer.apply(tjwt);
                if (token != null){
                    if (checkingOnExpiredTime(token)) {
                        filterChain.doFilter(request, response);
                    } else {
                        // TODO Token is expired
                        System.err.println("Token is expired");
                        throw new AccessDeniedException("User must be authenticated");
                    }
                } else {
                    // TODO Token is not valid
                    System.err.println("Token is not valid");
                    throw new AccessDeniedException("User must be authenticated");
                }
            } else {
                // TODO Token is empty
                System.err.println("Token is empty");
                throw new AccessDeniedException("User must be authenticated");
            }

        } else {

            filterChain.doFilter(request, response);
        }



    }

    private boolean checkingOnExpiredTime(Token token) {
        Boolean b = token.expiresAt().isAfter(Instant.now());
        return b;
    }
}
