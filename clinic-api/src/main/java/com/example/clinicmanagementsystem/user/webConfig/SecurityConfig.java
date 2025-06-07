package com.example.clinicmanagementsystem.user.webConfig;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
                        "/user/api/v1/auth/**",
                        "/user/api/v1/registration/**",
                        "/user/api/v1/register/**"
                    ).permitAll()

                    .requestMatchers(
                            HttpMethod.GET,
                            "/user/api/v1/verify-email",
                            "/user/api/v1/password-reset/**"
                    ).permitAll()
                    .requestMatchers(
                            HttpMethod.POST,
                            "/user/api/password-reset/request"
                    ).permitAll()


                    // API documentation
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll()

                    // All other requests require authentication
                    .anyRequest().authenticated();
            })
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> {
                log.debug("Configuring exception handling");
                exception.authenticationEntryPoint((request, response, authException) -> {
                    log.error("Authentication failed: {}", authException.getMessage());
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