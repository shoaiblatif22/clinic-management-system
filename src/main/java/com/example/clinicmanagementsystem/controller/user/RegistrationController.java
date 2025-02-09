package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.service.KeycloakService;
import com.example.clinicmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController()
@RequestMapping(value = "/registration")
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {
    private final UserService userService;
    private final KeycloakService keycloakService;

    public RegistrationController(UserService userService, KeycloakService keycloakService) {
        this.userService = userService;
        this.keycloakService = keycloakService;
    }

    @PostMapping("/register")
    public ResponseEntity<RedirectView> register(@RequestBody UserModel user) {
        try {
            userService.registerUser(user, keycloakService.toString());
            RedirectView redirectView = new RedirectView("/api/auth/user/patient");
            return ResponseEntity.status(HttpStatus.CREATED).body(redirectView);
        } catch (Exception e) {
            RedirectView redirectView = new RedirectView("/registration/error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(redirectView);
        }
    }
}
