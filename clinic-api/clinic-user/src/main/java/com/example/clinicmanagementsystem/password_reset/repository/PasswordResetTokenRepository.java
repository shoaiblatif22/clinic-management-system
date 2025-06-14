package com.example.clinicmanagementsystem.password_reset.repository;

import com.example.clinicmanagementsystem.password_reset.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(UserEntity user);
}
