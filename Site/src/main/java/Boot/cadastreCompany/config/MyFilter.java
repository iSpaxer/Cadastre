package Boot.cadastreCompany.config;

import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.AuthenticationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;

class MyFilter { //implements Filter {
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        String s = request.getServletPath();
//        System.err.println(s);
//        if ("/api/login".equals(request.getServletPath())
//                && HttpMethod.POST.matches(request.getMethod())) {
//            EngineerDTO engineerDTO = MAPPER.readValue(request.getInputStream(), EngineerDTO.class);
//
//            try {
//                request.login(engineerDTO.getLogin(), engineerDTO.getPassword());
//            } catch (ServletException e) {
//                throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//
//        // Выполнить действия до продолжения фильтрации
//        chain.doFilter(request, response); // Продолжить фильтрацию
//        // Выполнить действия после фильтрации
//    }
}
