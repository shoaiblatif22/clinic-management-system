package com.example.clinicmanagementsystem.login.service;

import com.example.clinicmanagementsystem.login.dto.LoginRequest;
import com.example.clinicmanagementsystem.login.dto.LoginResponse;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

        log.info("User authenticated - email: {}, role: {}, verified: {}",
                userEntity.getEmailAddress(), userEntity.getUserRole(), userEntity.isEnabled());

        return LoginResponse.builder()
                .token(token)
                .email(userEntity.getEmailAddress())
                .role(userEntity.getUserRole())
                .frontendUrl(frontendLoginUrl)
                .build();
    }
}
