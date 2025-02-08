package com.example.clinicmanagementsystem.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
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
