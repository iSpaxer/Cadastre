package Boot.cadastreCompany.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.stereotype.Service;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@Service
public class SecurityConfig  {

    private final AuthenticationProvider authProvider;
    private ConfigurableBeanFactory beanFactory;

    @Autowired
    public SecurityConfig(AuthenticationProvider authProvider, ConfigurableBeanFactory beanFactory) {
        this.authProvider = authProvider;
        this.beanFactory = beanFactory;
    }

//    @Bean("securityFilterChain")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        var chain = http
                .csrf().disable() ///TODO csrf and cors
//                .cors().disable()

                .cors()
//
                .and()
//                .csrf().csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
//                .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
//                .sessionAuthenticationStrategy(new CsrfAuthenticationStrategy(httpSessionCsrfTokenRepository))
//                .and()

                .authorizeHttpRequests()
                .requestMatchers("/", "/login", "/error").permitAll()
                .requestMatchers("/img/**", "/css/**", "/js/**", "/sass/**", "/libs/**", "/images/**", "/vendors/**").permitAll()
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/saveClient").permitAll()

                .requestMatchers("/api/**").authenticated() //TODO authenticated
                .requestMatchers("/adminPanel").authenticated()
                .anyRequest().authenticated() //TODO

                .and()
                .addFilterBefore(new UserPassAuthFilter(), BasicAuthenticationFilter.class)


                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/adminPanel")
                .failureUrl("/login?error")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")

                .and()
                .rememberMe().key("alexandr")//TODO key must be included from another file

                .and()
                .authenticationProvider(authProvider)
                .exceptionHandling(customizer -> customizer

                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .build();

//        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
//        beanFactory.registerSingleton("rememberMeServices", rememberMeServices);


        return chain;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
     AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }




}
