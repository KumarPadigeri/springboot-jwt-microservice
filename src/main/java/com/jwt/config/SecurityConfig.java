package com.jwt.config;


import com.jwt.filter.JwtAuthFilter;
import com.jwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    private final JwtAuthFilter authFilter;
    private final  UserRepository repository;
    public SecurityConfig(JwtAuthFilter authFilter,UserRepository repository ) {
        this.authFilter = authFilter;
        this.repository = repository;
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
            "/health/**",
            "/jwt/addUser",
            "jwt/getToken"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
       *//* var admin = User.builder()
                .username(applicationProperties.getAuth().getRoles().getAdmin().getUsername())
                .password(encoder().encode(applicationProperties.getAuth().getRoles().getAdmin().getPassword()))
                .roles("ADMIN", "USER")
                .build();
        var user = User.builder()
                .username(applicationProperties.getAuth().getRoles().getUser().getUsername())
                .password(encoder().encode(applicationProperties.getAuth().getRoles().getUser().getPassword()))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);*//*
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return null;
            }
        };
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf().disable()

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/jwt/**").permitAll()
                        .requestMatchers("/pet/**").permitAll()
                        .requestMatchers("/management/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return http.build();
    }


   */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }


    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService(repository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf().disable()

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/test/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/jwt/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/pet/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/management/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

