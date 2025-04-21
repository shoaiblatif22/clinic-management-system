package com.example.clinicmanagementsystem.user.registration.controller;

import com.example.clinicmanagementsystem.user.registration.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.user.registration.entity.VerificationToken;
import com.example.clinicmanagementsystem.user.registration.repository.AppUserRepository;
import com.example.clinicmanagementsystem.user.registration.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationVerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private AppUserRepository userRepository;

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }
        VerificationToken verificationToken = optionalToken.get();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired.");
        }
        ClinicAppUser user = verificationToken.getUser();
        user.setLocked(false);
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
        return ResponseEntity.ok("Account verified!");
    }
}