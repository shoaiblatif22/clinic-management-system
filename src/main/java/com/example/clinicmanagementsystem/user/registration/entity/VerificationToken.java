package com.example.clinicmanagementsystem.user.registration.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private ClinicAppUser user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public VerificationToken() {}

    public VerificationToken(String token, ClinicAppUser user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ClinicAppUser getUser() {
        return user;
    }

    public void setUser(ClinicAppUser user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
        
}