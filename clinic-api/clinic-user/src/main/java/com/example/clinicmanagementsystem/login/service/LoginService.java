package com.example.clinicmanagementsystem.login.service;

import com.example.clinicmanagementsystem.login.dto.LoginRequest;
import com.example.clinicmanagementsystem.login.dto.LoginResponse;

public interface LoginService {

    /**
     * Authenticates a user and returns a JWT-backed login response.
     *
     * @param loginRequest the credentials to authenticate
     * @return a populated LoginResponse containing the JWT token and user details
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are invalid
     */
    LoginResponse login(LoginRequest loginRequest);
}