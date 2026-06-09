package com.example.clinicmanagementsystem.registration.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.entity.UserVerificationToken;
import com.example.clinicmanagementsystem.registration.repository.UserVerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Listens for {@link RegistrationCompleteEvent}s and sends the email
 * verification message after creating (or refreshing) a verification token.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteListener {

    private static final long TOKEN_VALIDITY_HOURS = 24;
    private static final String VERIFY_EMAIL_PATH = "/user/api/v1/verify-email?token=";
    private static final String EMAIL_SUBJECT = "Email Verification";

    private final UserVerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    @EventListener
    public void handleRegistrationCompleteEvent(RegistrationCompleteEvent event) {
        UserEntity user = event.userEntity();
        log.info("Handling registration complete event for user: {}", user.getEmailAddress());

        String token = UUID.randomUUID().toString();
        saveVerificationToken(user, token);

        sendVerificationEmail(user, event.appUrl(), token);
    }

    /**
     * Creates a new verification token for the user, or refreshes the existing one.
     */
    private void saveVerificationToken(UserEntity user, String token) {
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(TOKEN_VALIDITY_HOURS);

        UserVerificationToken verificationToken = tokenRepository.findByUser(user)
                .map(existing -> existing.toBuilder()
                        .token(token)
                        .expiryDate(expiryDate)
                        .build())
                .orElseGet(() -> UserVerificationToken.builder()
                        .token(token)
                        .user(user)
                        .expiryDate(expiryDate)
                        .build());

        tokenRepository.save(verificationToken);
    }

    /**
     * Sends the verification email containing the activation link.
     */
    private void sendVerificationEmail(UserEntity user, String appUrl, String token) {
        String verificationUrl = appUrl + VERIFY_EMAIL_PATH + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailAddress());
        mailMessage.setSubject(EMAIL_SUBJECT);
        mailMessage.setText("Thank you for registering. Please click the link below to verify your email address:\n"
                + verificationUrl);

        mailSender.send(mailMessage);
        log.info("Verification email sent to: {}", user.getEmailAddress());
    }
}