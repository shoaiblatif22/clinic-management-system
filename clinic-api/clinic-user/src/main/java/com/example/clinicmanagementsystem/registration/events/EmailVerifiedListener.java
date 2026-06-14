package com.example.clinicmanagementsystem.registration.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailVerifiedListener {

    private static final String WELCOME_SUBJECT = "Welcome to the Clinic Management System";

    private final JavaMailSender mailSender;

    @EventListener
    public void handleEmailVerifiedEvent(EmailVerifiedEvent event) {
        UserEntity user = event.userEntity();
        log.info("Email verified for user - email: {}, role: {}, account enabled: {}",
                user.getEmailAddress(),
                user.getUserRole(),
                user.isEnabled());

        sendWelcomeEmail(user);
    }

    private void sendWelcomeEmail(UserEntity user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmailAddress());
        message.setSubject(WELCOME_SUBJECT);
        message.setText("Hi " + user.getFirstName() + ",\n\n"
                + "Your email has been verified and your account is now active.\n"
                + "You can now log in and start using the Clinic Management System.\n\n"
                + "Welcome aboard!");

        mailSender.send(message);
        log.info("Welcome email sent to: {}", user.getEmailAddress());
    }
}