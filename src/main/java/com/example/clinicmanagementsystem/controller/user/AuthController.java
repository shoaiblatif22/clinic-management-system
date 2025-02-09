package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.repository.KeycloakRepository;
import com.example.clinicmanagementsystem.service.KeycloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final KeycloakService keycloakService;

    public AuthController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    //Keycloak controller

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserModel userModel) {
        return keycloakService.registerUser(userModel);
    }

    @GetMapping("/user/patient")
    @PreAuthorize("hasRole('client_patient')")
    public RedirectView redirectClientPatientToDashboard() {
        return new RedirectView("/patient_dashboard");
    }

    @GetMapping("/user/doctor")
    @PreAuthorize("hasRole('client_doctor')")
    public String redirectClientDoctorToDashboard() {
        return "Hello Doctor";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('client_admin')")
    public String redirectClientAdminToDashboard() {
        return "redirect:/dashboard";
    }

}
