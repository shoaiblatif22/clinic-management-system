package com.example.clinicmanagementsystem.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = ClinicAppUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private ClinicAppUser clinicAppUser;
    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
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

    public ClinicAppUser getClinicAppUser() {
        return clinicAppUser;
    }

    public void setClinicAppUser(ClinicAppUser clinicAppUser) {
        this.clinicAppUser = clinicAppUser;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public VerificationToken(Long id, String token, ClinicAppUser clinicAppUser, Date expiryDate) {
        this.id = id;
        this.token = token;
        this.clinicAppUser = clinicAppUser;
        this.expiryDate = expiryDate;
    }
}
