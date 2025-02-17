package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/registration")
@CrossOrigin
public class RegistrationController {

    private final KeycloakService keycloakService;
    @Autowired
    public RegistrationController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel) {
        return keycloakService.registerUser(userModel);
    }
}
