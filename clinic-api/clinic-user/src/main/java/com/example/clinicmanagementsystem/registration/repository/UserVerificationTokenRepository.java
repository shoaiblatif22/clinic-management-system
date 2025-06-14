package com.example.clinicmanagementsystem.registration.repository;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.entity.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Long> {
    Optional<UserVerificationToken> findByToken(String token);
    Optional<UserVerificationToken> findByUser(UserEntity user);
}