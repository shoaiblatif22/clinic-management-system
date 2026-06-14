package com.example.clinicmanagementsystem.registration.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.entity.UserVerificationToken;
import com.example.clinicmanagementsystem.registration.repository.UserVerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationCompleteListenerTest {

    @Mock UserVerificationTokenRepository tokenRepository;
    @Mock JavaMailSender mailSender;

    @InjectMocks
    RegistrationCompleteListener listener;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .emailAddress("user@example.com")
                .firstName("Alice")
                .locked(true)
                .enabled(false)
                .build();
    }

    @Test
    void handleRegistrationCompleteEvent_newUser_savesTokenAndSendsEmail() {
        when(tokenRepository.findByUser(user)).thenReturn(Optional.empty());

        listener.handleRegistrationCompleteEvent(new RegistrationCompleteEvent(user, "http://app"));

        verify(tokenRepository).save(any(UserVerificationToken.class));

        ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(mailCaptor.capture());
        SimpleMailMessage mail = mailCaptor.getValue();
        assertEquals("user@example.com", mail.getTo()[0]);
        assertEquals("Email Verification", mail.getSubject());
        assertTrue(mail.getText().contains("http://app/user/api/v1/verify-email?token="));
    }

    @Test
    void handleRegistrationCompleteEvent_existingToken_refreshesTokenAndSendsEmail() {
        UserVerificationToken existing = UserVerificationToken.builder()
                .token("old-token")
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();
        when(tokenRepository.findByUser(user)).thenReturn(Optional.of(existing));

        listener.handleRegistrationCompleteEvent(new RegistrationCompleteEvent(user, "http://app"));

        ArgumentCaptor<UserVerificationToken> tokenCaptor = ArgumentCaptor.forClass(UserVerificationToken.class);
        verify(tokenRepository).save(tokenCaptor.capture());
        assertNotEquals("old-token", tokenCaptor.getValue().getToken()); // refreshed

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
