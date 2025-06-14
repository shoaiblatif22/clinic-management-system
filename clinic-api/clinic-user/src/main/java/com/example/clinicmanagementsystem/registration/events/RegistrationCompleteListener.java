package com.example.clinicmanagementsystem.registration.events;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.entity.UserVerificationToken;
import com.example.clinicmanagementsystem.registration.repository.UserVerificationTokenRepository;

@Component
public class RegistrationCompleteListener {
    @Autowired
    private UserVerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender; 

    @EventListener
    public void handleRegistrationCompleteEvent(RegistrationCompleteEvent event) {
        UserEntity user = event.userEntity();
        
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        
        // Try to find an existing token for this user
        Optional<UserVerificationToken> existingTokenOpt = tokenRepository.findByUser(user);
        UserVerificationToken userVerificationToken;
        if (existingTokenOpt.isPresent()) {
            // Update the existing token using builder pattern
            UserVerificationToken existingToken = existingTokenOpt.get();
            userVerificationToken = existingToken.toBuilder()
                    .token(token)
                    .expiryDate(expiryDate)
                    .build();
        } else {
            // Create a new token if none exists using builder pattern
            userVerificationToken = UserVerificationToken.builder()
                    .token(token)
                    .user(user)
                    .expiryDate(expiryDate)
                    .build();
        }
        tokenRepository.save(userVerificationToken);

        String verificationUrl = event.appUrl() + "/user/api/v1/verify-email?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailAddress());
        mailMessage.setSubject("Email Verification");
        mailMessage.setText("Thank you for registering. Please click the link below to verify your email address:\n" + verificationUrl);
        mailSender.send(mailMessage);
    }
}
