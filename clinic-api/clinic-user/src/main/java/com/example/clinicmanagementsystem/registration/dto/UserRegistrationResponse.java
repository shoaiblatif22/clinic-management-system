package com.example.clinicmanagementsystem.registration.dto;

import com.example.clinicmanagementsystem.registration.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String addressLineOne;
    private String addressLineTwo;
    private String townOrCity;
    private String postcode;
    private String county;
    private String country;
    private UserRole userRole;
    private Boolean locked;
    private Boolean enabled;
}