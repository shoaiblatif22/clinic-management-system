package com.example.clinicalmanagementsystem.unit_tests.UnitTestResetPassword;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mail.MailSendException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.clinicmanagementsystem.user.password_reset.events.PasswordResetEvent;
import com.example.clinicmanagementsystem.user.password_reset.events.PasswordResetListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(SpringExtension.class)
public class PasswordResetEventListenerUnitTests {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private PasswordResetListener passwordResetListener;

    private UserEntity testUser;
    private final String testToken = "test-token-123";

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setEmailAddress("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
    }

    @Test
    @DisplayName("Test 1: Password reset event which should send an email with a reset link")
    void handlePasswordResetEvent_ShouldSendEmailWithResetLink() {
        // Arrange
        PasswordResetEvent event = new PasswordResetEvent(testUser, testToken, "http://test-app.com");

        // Act
        passwordResetListener.handlePasswordResetEvent(event);

        // Assert
        ArgumentCaptor<SimpleMailMessage> mailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(mailMessageCaptor.capture());

        SimpleMailMessage sentMessage = mailMessageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(testUser.getEmailAddress(), sentMessage.getTo()[0]);
        assertEquals("Password Reset Request", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("http://test-app.com/reset-password?token=" + testToken));
    }

    @Test
    @DisplayName("Test 2: Password reset event which should use a default url when not provided")
    void handlePasswordResetEvent_ShouldUseDefaultUrlWhenNotProvided() {
        // Arrange
        PasswordResetEvent event = new PasswordResetEvent(testUser, testToken, null);

        // Act
        passwordResetListener.handlePasswordResetEvent(event);

        // Assert
        ArgumentCaptor<SimpleMailMessage> mailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(mailMessageCaptor.capture());

        SimpleMailMessage sentMessage = mailMessageCaptor.getValue();
        assertTrue(sentMessage.getText().contains("http://localhost:5173/reset-password?token=" + testToken));
    }

    @Test
    @DisplayName("Test 3: Password reset event should log error when email fails")
    void handlePasswordResetEvent_ShouldLogErrorWhenEmailFails() {
        // Arrange
        PasswordResetEvent event = new PasswordResetEvent(testUser, testToken, "http://test-app.com");
        doThrow(new MailSendException("Failed to send email")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                passwordResetListener.handlePasswordResetEvent(event)
        );
    }
}
