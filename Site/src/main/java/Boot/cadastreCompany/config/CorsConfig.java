package Boot.cadastreCompany.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
        registry
                .addMapping("/**")                  // обращаться к нашему приложению можно по любому внутреннему url — addMapping(«/**»
//                .allowedOrigins("https://www.google.ru/")
                .allowedOrigins("http://localhost:8080")      //  только сайт и бд http://localhost:8080 может делать запросы
                .allowedOrigins("http://localhost:8088")
//                .allowedOrigins("http://127.0.0.1:5500")    //TODO
                .allowedMethods("*");                         //  запрос можно делать абсолютно всеми методами (GET, POST, PUT и т.д.)
//                .allowCredentials()

    }
}
