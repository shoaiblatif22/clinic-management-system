package com.api_gateway.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class WebFluxSecurityConfig  {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    static SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/user/api/v1/auth/login", "/user/api/v1/registration/verify", "/user/api/v1/auth/register").permitAll()
                .anyExchange().authenticated()
            );
        return http.build();
    }
}

