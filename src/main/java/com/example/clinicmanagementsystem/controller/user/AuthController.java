package com.example.clinicmanagementsystem.controller.user;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

//    private final KeycloakService keycloakService;
//    @Autowired
//    public AuthController(KeycloakService keycloakService) {
//        this.keycloakService = keycloakService;
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@RequestBody UserModel userModel) {
//        return keycloakService.registerUser(userModel);
//    }


    @GetMapping("/user/patient")
    @PreAuthorize("hasRole('client_patient')")
    public RedirectView redirectClientPatientToDashboard() {
        return new RedirectView("/patient_dashboard");
    }

    @GetMapping("/user/doctor")
    @PreAuthorize("hasRole('client_doctor')")
    public RedirectView redirectClientDoctorToDashboard() {
        return new RedirectView("/doctor_dashboard");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('client_admin')")
    public RedirectView redirectClientAdminToDashboard() {
        return new RedirectView("/dashboard");
    }
}
