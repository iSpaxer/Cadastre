package Boot.cadastreCompany.config;

import Boot.cadastreCompany.security.AuthProviderImpl;
import Boot.cadastreCompany.security.EngDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Service;


@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@Service
public class SecurityConfig  {

//    private final EngDetailsServiceImpl engDetailsService;
    private final AuthenticationProvider authProvider;

    @Autowired
    public SecurityConfig(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() ///TODO csrf and cors
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/", "/error").permitAll()
                .requestMatchers("/img/**", "/css/**", "/js/**", "/sass/**", "/libs/**").permitAll()
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/adminPanel").authenticated()
                .anyRequest().denyAll()
                .and()
                .authenticationProvider(authProvider)
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }



}
