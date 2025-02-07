package com.example.clinicmanagementsystem.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/registration")
public class RegistrationController {
    @GetMapping("/form")
    public String form() {
        return "Registration Form";
    }
}
