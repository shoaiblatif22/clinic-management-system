package com.example.clinicmanagementsystem.user.registration.events;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.clinicmanagementsystem.user.registration.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.user.registration.entity.VerificationToken;
import com.example.clinicmanagementsystem.user.registration.repository.VerificationTokenRepository;

@Component
public class RegistrationCompleteListener {
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender; 

    @EventListener
    public void handleRegistrationCompleteEvent(RegistrationCompleteEvent event) {
        ClinicAppUser user = event.clinicAppUser();
        
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        
        // Try to find an existing token for this user
        Optional<VerificationToken> existingTokenOpt = tokenRepository.findByUser(user);
        VerificationToken verificationToken;
        if (existingTokenOpt.isPresent()) {
            // Update the existing token
            verificationToken = existingTokenOpt.get();
            verificationToken.setToken(token);
            verificationToken.setExpiryDate(expiryDate);
        } else {
            // Create a new token if none exists
            verificationToken = new VerificationToken(token, user, expiryDate);
        }
        tokenRepository.save(verificationToken);

        String verificationUrl = event.appUrl() + "/api/v1/registration/verify?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailAddress());
        mailMessage.setSubject("Email Verification");
        mailMessage.setText("Thank you for registering. Please click the link below to verify your email address:\n" + verificationUrl);
        mailSender.send(mailMessage);
    }
}
