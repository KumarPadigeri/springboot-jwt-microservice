package com.practice.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/*
 * @Created 1/10/2023 12:56 PM 2023
 * @Project testing-project-springboot3
 * @User Kumar Padigeri
 */
@Configuration
@EnableJpaAuditing
@Slf4j
public class SecurityConfig {

    private final ApplicationProperties applicationProperties;

    public SecurityConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    private static final String[] AUTH_WHITELIST = {
            "/",
            "/error",
            "/api-docs/**",
            "/swagger-ui/index.html",
            "/swagger-ui/index.html/**",
            "/documentation",
            "/documentation/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "webjars/**",
            // -- Swagger UI v3
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // CSA Controllers
            "/csa/api/token",
            // Actuators
            "/actuator/**",
            "/health/**"
    };

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var admin = User.builder()
                .username(applicationProperties.getAuth().getRoles().getAdmin().getUsername())
                .password(encoder().encode(applicationProperties.getAuth().getRoles().getAdmin().getPassword()))
                .roles("ADMIN", "USER")
                .build();
        var user = User.builder()
                .username(applicationProperties.getAuth().getRoles().getUser().getUsername())
                .password(encoder().encode(applicationProperties.getAuth().getRoles().getUser().getPassword()))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/test/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/pet/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/management/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

}

