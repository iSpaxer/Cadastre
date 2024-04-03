package TgBot.jwt;

import TgBot.jwt.factory.DefaultTelegramTokenFactory;
import TgBot.jwt.model.Token;
import TgBot.jwt.model.Tokens;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@Slf4j
@Component
public class TelegramJwtFactory implements Function<Update, String> {

    private Function<Update, Token> defaultTelegramTokenFactory = new DefaultTelegramTokenFactory();

    private final Function<Token, String> telegramTokenSerializer;

    private ObjectMapper objectMapper = new ObjectMapper();

    public TelegramJwtFactory(Function<Token, String> telegramTokenSerializer) {
        this.telegramTokenSerializer = telegramTokenSerializer;
    }

    @Override
    public String apply(Update update) {
        var token = defaultTelegramTokenFactory.apply(update);
        var telegramTokenString = telegramTokenSerializer.apply(token);
        var tokens = new Tokens(
                telegramTokenString,
                String.valueOf(token.expiresAt())
        );
        if (true) { // TODO testing
            return telegramTokenString;
        }
        try {
            return objectMapper.writeValueAsString(tokens);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("Error mapp: " + telegramTokenString);
//        try {
//             return objectMapper.writeValueAsString(new Tokens(telegramTokenString, token.expiresAt().toString()));
//        } catch (JsonProcessingException e) {
//            log.error("Error created TJWT: " + e.getMessage());
//            throw new RuntimeException(e);
//        }
    }
}
