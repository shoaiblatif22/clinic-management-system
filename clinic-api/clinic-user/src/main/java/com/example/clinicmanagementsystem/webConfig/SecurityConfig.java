package com.example.clinicmanagementsystem.webConfig;

import com.example.clinicmanagementsystem.registration.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security configuration for the application.
 * Configures authentication and authorization rules.
 */
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final UserRepository userRepository;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByEmailAddressIgnoreCase(username)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> {
//                    //log.debug("Configuring CORS with custom configuration");
//                    cors.configurationSource(corsConfigurationSource());
//                })
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/user/api/v1/register",
//                                "/user/api/v1/login",
//                                "/user/api/v1/verify-email",
//                                "/user/api/v1/password-reset/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//
//
//        return http.build();
//    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        //log.debug("Configuring CORS settings");
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowedHeaders(List.of(
//                "Authorization", "Content-Type", "X-Requested-With",
//                "Accept", "Origin", "Referer", "User-Agent"
//        ));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        //log.info("CORS configuration applied to all endpoints");
//        return source;
//    }
//}

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${app.api.registerApi}")
    private String REGISTER;
    @Value("${app.api.passwordReset}")
    private String PASSWORD_RESET;
    @Value("${app.api.verifyEmail}")
    private String VERIFY_EMAIL;

    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring security filter chain...");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    log.debug("Configuring CORS with custom configuration");
                    cors.configurationSource(corsConfigurationSource());
                })
                .authorizeHttpRequests(auth -> {
                    log.debug("Configuring authorization rules");
                    auth
                            // Public endpoints
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                            // Auth endpoints
                            .requestMatchers(
                                    HttpMethod.POST,
                                    REGISTER,
                                    "/user/api/v1/password-reset/**",
                                    "/user/api/v1/login"
                            ).permitAll()

                            .requestMatchers(
                                    HttpMethod.GET,
                                    VERIFY_EMAIL,
                                    "/user/api/v1/password-reset/**",
                                    "/user/api/v1/login"
                            ).permitAll()

                            // API documentation
                            .requestMatchers(
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/swagger-resources/**",
                                    "/webjars/**",
                                    "/error"
                            ).permitAll()

                            // All other requests require authentication
                            .anyRequest().authenticated();
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> {
                    log.debug("Configuring exception handling");
                    exception.authenticationEntryPoint((request, response, authException) -> {
                        log.error("Authentication failed for path {}: {}", request.getRequestURI(), authException.getMessage());
                        response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                "Unauthorized: " + authException.getMessage()
                        );
                    });
                })
                .sessionManagement(session -> {
                    log.debug("Configuring stateless session management");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        log.info("Security filter chain configuration completed");
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.debug("Configuring CORS settings");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of(
                "Authorization", "Content-Type", "X-Requested-With",
                "Accept", "Origin", "Referer", "User-Agent"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        log.info("CORS configuration applied to all endpoints");
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("Creating BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();

    }
}