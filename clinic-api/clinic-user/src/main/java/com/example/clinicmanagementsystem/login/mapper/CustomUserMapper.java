package com.example.clinicmanagementsystem.login.mapper;


import com.example.clinicmanagementsystem.login.dto.LoginResponse;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between UserEntity and LoginResponse
 */
@Component
public class CustomUserMapper {

    @Value("${app.frontend.login-url}")
    private String frontendLoginUrl;

    /**
     * Maps a UserEntity and JWT token to a LoginResponse
     * 
     * @param userEntity The user entity to map
     * @param token The JWT token for the user
     * @return A LoginResponse containing the token and user information
     */
    public LoginResponse toLoginResponse(UserEntity userEntity, String token) {
        return LoginResponse.builder()
                .token(token)
                .email(userEntity.getEmailAddress())
                .role(userEntity.getUserRole())
                .frontendUrl(frontendLoginUrl)
                .build();
    }
}
