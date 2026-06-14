package com.example.clinicmanagementsystem.password_reset.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetListenerTest {

    @Mock JavaMailSender mailSender;

    @InjectMocks
    PasswordResetListener listener;

    private UserEntity user;
    private static final String TOKEN = "test-token-123";

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .emailAddress("test@example.com")
                .firstName("Test")
                .locked(false)
                .enabled(true)
                .build();
    }

    @Test
    void handlePasswordResetEvent_sendsEmailWithResetLink() {
        PasswordResetEvent event = new PasswordResetEvent(user, TOKEN, "http://app.com");

        listener.handlePasswordResetEvent(event);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        SimpleMailMessage mail = captor.getValue();
        assertEquals("test@example.com", mail.getTo()[0]);
        assertEquals("Password Reset Request", mail.getSubject());
        assertTrue(mail.getText().contains("http://app.com/reset-password?token=" + TOKEN));
    }

    @Test
    void handlePasswordResetEvent_nullAppUrl_usesDefaultUrl() {
        PasswordResetEvent event = new PasswordResetEvent(user, TOKEN, null);

        listener.handlePasswordResetEvent(event);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        assertTrue(captor.getValue().getText().contains("http://localhost:5173/reset-password?token=" + TOKEN));
    }

    @Test
    void handlePasswordResetEvent_mailFailure_throwsRuntimeException() {
        doThrow(new MailSendException("send failed")).when(mailSender).send(any(SimpleMailMessage.class));

        assertThrows(RuntimeException.class,
                () -> listener.handlePasswordResetEvent(new PasswordResetEvent(user, TOKEN, "http://app.com")));
    }
}
