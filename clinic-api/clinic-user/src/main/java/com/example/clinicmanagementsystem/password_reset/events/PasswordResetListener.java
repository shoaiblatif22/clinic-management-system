package com.example.clinicmanagementsystem.password_reset.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Listener for password reset events that handles sending password reset emails.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordResetListener {
    private final JavaMailSender mailSender;

    /**
     * Handles the password reset event by sending an email with the reset link.
     *
     * @param event the password reset event containing user and token information
     */
    @Async
    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent event) {
        UserEntity user = event.userEntity();
        String token = event.token();
        
        // Use the provided appUrl or default to localhost:5173 if not provided
        String baseUrl = event.appUrl() != null ? event.appUrl() : "http://localhost:5173";
        String resetPath = "/reset-password?token=";
        String resetUrl = baseUrl + resetPath + token;
        
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmailAddress());
            mailMessage.setSubject("Password Reset Request");
            mailMessage.setText(
                "You have requested to reset your password.\n\n" +
                "Please click the link below to reset your password:\n" + 
                resetUrl + "\n\n" +
                "This link will expire in 24 hours.\n" +
                "If you didn't request this, please ignore this email and your password will remain unchanged.\n\n" +
                "For security reasons, this link will expire in 24 hours."
            );
            
            mailSender.send(mailMessage);
            log.info("Password reset email sent to: {}", user.getEmailAddress());
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", user.getEmailAddress(), e);
            // In a production environment, consider adding retry logic or dead-letter queue for failed emails
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
}
