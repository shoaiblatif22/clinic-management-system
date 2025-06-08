package com.example.clinicalmanagementsystem.tests.UnitTestResetPassword;

import com.example.clinicmanagementsystem.user.password_reset.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.user.password_reset.repository.PasswordResetTokenRepository;
import com.example.clinicmanagementsystem.user.password_reset.service.PasswordResetServiceImpl;
import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.user.registration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordRestServiceImplUnitTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetService;

    private UserEntity testUser;
    private PasswordResetToken testToken;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setEmailAddress("test@example.com");
        testUser.setPassword("oldPassword");
        testUser.setEnabled(true);

        testToken = new PasswordResetToken("valid-token", testUser);
    }

    @Test
    void createPasswordResetToken_NewToken() {
        when(tokenRepository.findByUser(testUser)).thenReturn(Optional.empty());

        passwordResetService.createPasswordResetToken(testUser, "new-token");

        verify(tokenRepository).save(any(PasswordResetToken.class));
    }

    @Test
    void validatePasswordResetToken_ValidToken() {
        when(tokenRepository.findByToken("valid-token"))
                .thenReturn(Optional.of(testToken));

        String email = passwordResetService.validatePasswordResetToken("valid-token");

        assertEquals("test@example.com", email);
    }

    @Test
    void validatePasswordResetToken_ExpiredToken() {
        testToken.setExpiryDate(new Date(System.currentTimeMillis() - 1000));
        when(tokenRepository.findByToken("expired-token"))
                .thenReturn(Optional.of(testToken));

        assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.validatePasswordResetToken("expired-token"));

        verify(tokenRepository).delete(testToken);
    }

    @Test
    void changeUserPassword_ValidPassword() {
        passwordResetService.changeUserPassword(testUser, "NewPass123#");

        verify(passwordEncoder).encode("NewPass123#");
        verify(userRepository).save(testUser);
    }

    @Test
    void changeUserPassword_InvalidPassword() {
        assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.changeUserPassword(testUser, "weak"));
    }

    @Test
    void requestPasswordReset_UserNotFound() {
        when(userRepository.findByEmailAddressIgnoreCase("nonexistent@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.requestPasswordReset("nonexistent@example.com"));
    }

    @Test
    void resetPassword_ValidInput() {
        when(tokenRepository.findByToken("valid-token"))
                .thenReturn(Optional.of(testToken));

        passwordResetService.resetPassword("valid-token", "NewPass123#");

        verify(passwordEncoder).encode("NewPass123#");
        verify(userRepository).save(testUser);
        verify(tokenRepository).delete(testToken);
    }
}
