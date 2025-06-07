package com.example.clinicmanagementsystem.user.resetpassword.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequest {
    
    @NotBlank(message = "Email address must not be empty")
    @Email(message = "Email should be valid")
    private String emailAddress;

    // Getters and Setters
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
