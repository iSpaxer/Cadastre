package Boot.cadastreCompany.config;

import Boot.cadastreCompany.service.impl.EngDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final EngDetailsServiceImpl engDetailsService;

    @Autowired
    public SecurityConfig(EngDetailsServiceImpl engDetailsService) {
        this.engDetailsService = engDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests( (auth) -> auth
                .requestMatchers("/login", "/", "/error", "/s").permitAll()
//                    .requestMatchers( "/css/**").permitAll()
                .anyRequest().authenticated())
            .httpBasic().and()
            .formLogin().loginPage("/login")
            .loginProcessingUrl("/process_login")
            .defaultSuccessUrl("/in", true)
            .failureUrl("/login?error");
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/img/**", "/css/**");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(engDetailsService);

        return authProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
