package ru.alex0d.javaspring.configs;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.alex0d.javaspring.services.UserService;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied"))
                .and()
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/js/**", "/css/**", "/login", "/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .rememberMe()
                    .tokenRepository(persistentTokenRepository(dataSource))
                    .userDetailsService(userService)
                    .tokenValiditySeconds(60 * 60 * 24 * 7)
                .and()
                .addFilterAfter(basicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BasicAuthenticationFilter basicAuthenticationFilter() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(authenticationProvider));
        return new BasicAuthenticationFilter(providerManager);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}