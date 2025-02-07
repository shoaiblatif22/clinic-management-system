package com.example.clinicmanagementsystem.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/doctor")
    @PreAuthorize("hasRole('client_doctor')")
    public String redirectClientDoctorToDashboard() {
        return "Hello Doctor";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('client_admin')")
    public String redirectClientAdminToDashboard() {
        return "redirect:/dashboard";
    }

   // https://stackoverflow.com/questions/39356300/avoid-keycloak-default-login-page-and-use-project-login-page

}
