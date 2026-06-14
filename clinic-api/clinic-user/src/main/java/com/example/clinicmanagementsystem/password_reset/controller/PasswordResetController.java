package com.example.clinicmanagementsystem.password_reset.controller;


import com.example.clinicmanagementsystem.password_reset.dto.PasswordResetRequest;
import com.example.clinicmanagementsystem.password_reset.dto.ResetPasswordRequest;
import com.example.clinicmanagementsystem.password_reset.service.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@RestController
@RequestMapping("/user/api/v1/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(
            @Valid @RequestBody PasswordResetRequest request,
            HttpServletRequest httpRequest) {
        try {
            log.info("Password reset request for {}", request.getEmailAddress());
            passwordResetService.requestPasswordReset(request.getEmailAddress());
            return ResponseEntity.status(HttpStatus.CREATED).body("Password reset email sent if account exists");
        } catch (IllegalArgumentException e) {
            log.warn("Invalid password reset request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @Valid @RequestBody ResetPasswordRequest request) {
        try {
            passwordResetService.resetPassword(token, request.getNewPassword());
            return ResponseEntity.ok("Password has been reset successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

