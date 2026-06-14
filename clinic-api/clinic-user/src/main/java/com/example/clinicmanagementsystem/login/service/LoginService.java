package com.example.clinicmanagementsystem.login.service;

import com.example.clinicmanagementsystem.login.dto.LoginRequest;
import com.example.clinicmanagementsystem.login.dto.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);
}