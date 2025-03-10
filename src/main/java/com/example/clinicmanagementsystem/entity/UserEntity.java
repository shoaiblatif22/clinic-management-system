package com.example.clinicmanagementsystem.entity;

import com.example.clinicmanagementsystem.model.AppUserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    // Getters and Setters for all the fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String keycloakId;
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

    public UserEntity(AppUserModel appUserModel, String keycloakId) {
    }
}

