package com.example.clinicmanagementsystem.controller;

import com.example.clinicmanagementsystem.entity.AppUser;
import com.example.clinicmanagementsystem.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser appUser, @RequestParam String password) {
        AppUser registeredUser = appUserService.regiserUser(appUser, password);
        return ResponseEntity.ok(registeredUser);  // Return the registered user or any relevant response
    }
}