package com.example.clinicmanagementsystem.user.login.dto;

import com.example.clinicmanagementsystem.user.registration.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private UserRole role;
    private String frontendUrl;
}
