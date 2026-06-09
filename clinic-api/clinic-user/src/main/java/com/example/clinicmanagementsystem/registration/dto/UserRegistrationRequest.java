package com.example.clinicmanagementsystem.registration.dto;

import com.example.clinicmanagementsystem.registration.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String emailAddress;

    private String addressLineOne;

    private String addressLineTwo;

    private String townOrCity;

    private String postcode;

    private String county;

    private String country;

    @NotBlank(message = "Password is required")
    private String password;

    private UserRole userRole;
}