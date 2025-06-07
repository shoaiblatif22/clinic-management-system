package com.example.clinicmanagementsystem.user.resetpassword.events;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.example.clinicmanagementsystem.user.resetpassword.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.user.resetpassword.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;

@Component
public class PasswordResetListener {
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent event) {
        UserEntity user = event.userEntity();
        String token = UUID.randomUUID().toString();

        // Find existing token for this user
        Optional<PasswordResetToken> existingTokenOpt = tokenRepository.findByUser(user);
        PasswordResetToken passwordResetToken;
        if (existingTokenOpt.isPresent()) {
            passwordResetToken = existingTokenOpt.get();
            passwordResetToken.setToken(token);
            passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));
        } else {
            passwordResetToken = new PasswordResetToken(token, user);
        }
        tokenRepository.save(passwordResetToken);

        // Construct the password reset URL to be sent in the email
        String resetUrl = "http://localhost:5173/reset-password?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailAddress());
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("You have requested to reset your password. Please click the link below to reset your password:\n" + resetUrl);
        mailSender.send(mailMessage);
    }
}
