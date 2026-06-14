package com.example.clinicmanagementsystem.login.service;

import com.example.clinicmanagementsystem.login.dto.LoginRequest;
import com.example.clinicmanagementsystem.login.dto.LoginResponse;
import com.example.clinicmanagementsystem.login.events.LoginEvent;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Value("${app.frontend.login-url}")
    private String frontendLoginUrl;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        String token = jwtService.generateToken(userEntity);

        eventPublisher.publishEvent(new LoginEvent(userEntity));

        log.info("User logged in: {}", userEntity.getEmailAddress());

        return LoginResponse.builder()
                .token(token)
                .email(userEntity.getEmailAddress())
                .role(userEntity.getUserRole())
                .frontendUrl(frontendLoginUrl)
                .build();
    }
}