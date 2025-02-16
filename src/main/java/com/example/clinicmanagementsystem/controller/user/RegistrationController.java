package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.service.KeycloakService;
import com.example.clinicmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/registration")
@CrossOrigin(origins = "*") // Allow multiple origins dynamically
public class RegistrationController {
    private final KeycloakService keycloakService;

    public RegistrationController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel) {
        return keycloakService.registerUser(userModel);
    }
}
