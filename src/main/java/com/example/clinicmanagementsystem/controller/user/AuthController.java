package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<ClinicAppUser> registerUser(@RequestBody ClinicAppUser clinicAppUser) {
        try {
            ClinicAppUser registeredUser = appUserService.registerUser(clinicAppUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
