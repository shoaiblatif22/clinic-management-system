package com.example.clinicmanagementsystem.user.resetpassword.service;

import com.example.clinicmanagementsystem.user.registration.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.user.registration.repository.AppUserRepository;
import com.example.clinicmanagementsystem.user.resetpassword.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.user.resetpassword.events.PasswordResetEvent;
import com.example.clinicmanagementsystem.user.resetpassword.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ClinicAppUser createPasswordResetToken(String emailAddress) {
        Optional<ClinicAppUser> userOptional = appUserRepository.findByEmailAddress(emailAddress);
    
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with email: " + emailAddress);
        }
    
        ClinicAppUser user = userOptional.get();
        String token = UUID.randomUUID().toString();
    
        // Check for existing token for this user
        Optional<PasswordResetToken> existingTokenOpt = tokenRepository.findByUser(user);
        PasswordResetToken resetToken;
        if (existingTokenOpt.isPresent()) {
            resetToken = existingTokenOpt.get();
            resetToken.setToken(token);
            resetToken.setExpiryDate(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))); // 24 hours
        } else {
            resetToken = new PasswordResetToken(token, user);
        }
        tokenRepository.save(resetToken);
    
        return user;
    }

    // Reset the password using the token
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.isExpired()) {
            throw new RuntimeException("Token expired");
        }

        ClinicAppUser user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));

        appUserRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}
