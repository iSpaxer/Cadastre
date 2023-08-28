package Boot.cadastreCompany.config;

import Boot.cadastreCompany.security.AuthProviderImpl;
import Boot.cadastreCompany.security.EngDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.stereotype.Service;


@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@Service
public class SecurityConfig  {

//    private final EngDetailsServiceImpl engDetailsService;
    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
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
                .requestMatchers("/adminPanel").authenticated()
                ///TODO .anyRequest().authenticated()
                .anyRequest().permitAll()
                //TODO
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                //          .formLogin().loginPage("/login")
                //.loginProcessingUrl("/process_login")
                //    .defaultSuccessUrl("/adminPanel", true)
                //     .failureUrl("/login?error")
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .authenticationProvider(authProvider);
//                .authenticationProvider(authenticationProvider());

        return http.build();
    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setPasswordEncoder(passwordEncoder());
////        authProvider.setUserDetailsService(engDetailsService);
//
//
//        return authProvider;
//    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
//        config.
//        return null;
//    }

}
