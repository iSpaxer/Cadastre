package Boot.cadastreCompany.config;

import java.io.IOException;


import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.AuthenticationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class UserPassAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {


        filterChain.doFilter(httpServletRequest, httpServletResponse);

//        String s = httpServletRequest.getServletPath();
//        System.err.println(s);
//        if ("/api/login".equals(httpServletRequest.getServletPath())
//                && HttpMethod.POST.matches(httpServletRequest.getMethod())) {
//
//            EngineerDTO engineerDTO = MAPPER.readValue(httpServletRequest.getInputStream(), EngineerDTO.class);
//
//            try {
//                httpServletRequest.login(engineerDTO.getLogin(), engineerDTO.getPassword());
//            } catch (ServletException e) {
//                throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
//            }
//        }
    }
}
