package com.example.clinicmanagementsystem.user.password_reset.repository;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.user.password_reset.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(UserEntity user);
}
