package com.example.clinicmanagementsystem.user.resetpassword.controller;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.user.registration.repository.UserRepository;
import com.example.clinicmanagementsystem.user.resetpassword.events.PasswordResetEvent;
import com.example.clinicmanagementsystem.user.resetpassword.repository.PasswordResetTokenRepository;
import com.example.clinicmanagementsystem.user.resetpassword.service.PasswordResetService;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import com.example.clinicmanagementsystem.user.resetpassword.dto.PasswordResetRequest;

import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping("/user/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService passwordResetService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(
            @Valid @RequestBody PasswordResetRequest request,
            HttpServletRequest httpRequest) {
        try {
            log.info("Password reset request for {}", request.getEmailAddress());
            passwordResetService.requestPasswordReset(request.getEmailAddress());
            
            // Get user for event publishing
            Optional<UserEntity> userOptional = userRepository.findByEmailAddress(request.getEmailAddress());
            if (userOptional.isPresent()) {
                String appUrl = httpRequest.getScheme() + "://" + httpRequest.getServerName() +
                        ":" + httpRequest.getServerPort();
                eventPublisher.publishEvent(new PasswordResetEvent(userOptional.get(), appUrl));
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body("Password reset email sent if account exists");
        } catch (IllegalArgumentException e) {
            log.warn("Invalid password reset request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password has been reset successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

