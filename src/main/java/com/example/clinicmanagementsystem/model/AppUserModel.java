package com.example.clinicmanagementsystem.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserModel {
    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only alphabetic characters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only alphabetic characters")
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    AppUserGender appUserGender;

    @NotNull(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    private String phoneNumber;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String emailAddress;
    private String addressLineOne;
    private String addressLineTwo;
    private String townOrCity;
    private String postcode;
    private String county;
    private String country;
    private String password;

}
