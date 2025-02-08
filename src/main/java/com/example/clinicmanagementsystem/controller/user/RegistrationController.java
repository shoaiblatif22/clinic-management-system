package com.example.clinicmanagementsystem.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController()
@RequestMapping(value = "/registration", method = RequestMethod.GET)
public class RegistrationController {
    @GetMapping("/register")
    public String form() {
        return null;
    }
}
