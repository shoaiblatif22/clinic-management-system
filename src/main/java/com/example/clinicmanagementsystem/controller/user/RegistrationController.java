package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.service.KeycloakService;
import com.example.clinicmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = "/registration")
@CrossOrigin(origins = "*") // Allow multiple origins dynamically
public class RegistrationController {
    private final UserService userService;
    private final KeycloakService keycloakService;

    public RegistrationController(UserService userService, KeycloakService keycloakService) {
        this.userService = userService;
        this.keycloakService = keycloakService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        try {
            // Register the user in Keycloak first
            String keycloakId = keycloakService.registerUser(user);

            // Wait for email verification before saving user to DB
            boolean isVerified = keycloakService.isEmailVerified(keycloakId);

            if (isVerified) {
                userService.registerUser(user, keycloakId);
                return ResponseEntity.status(HttpStatus.CREATED).body(new RedirectView("/api/auth/user/patient"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not verified. Please check your inbox.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
