package com.example.unittests.UnitTestResetPassword;

import com.example.clinicmanagementsystem.password_reset.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.password_reset.repository.PasswordResetTokenRepository;
import com.example.clinicmanagementsystem.password_reset.service.PasswordResetServiceImpl;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PasswordResetServiceImplUnitTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    @DisplayName("Test 1")
    void createPasswordResetToken_NewToken() {
        when(tokenRepository.findByUser(testUser)).thenReturn(Optional.empty());

        passwordResetService.createPasswordResetToken(testUser, "new-token");

        verify(tokenRepository).save(any(PasswordResetToken.class));
    }

    @Test
    @DisplayName("Test 2")
    void validatePasswordResetToken_ValidToken() {
        when(tokenRepository.findByToken("valid-token"))
                .thenReturn(Optional.of(testToken));

        String email = passwordResetService.validatePasswordResetToken("valid-token");

        assertEquals("test@example.com", email);
    }

    @Test
    @DisplayName("Test 3")
    void validatePasswordResetToken_ExpiredToken() {
        testToken.setExpiryDate(new Date(System.currentTimeMillis() - 1000));
        when(tokenRepository.findByToken("expired-token"))
                .thenReturn(Optional.of(testToken));

        assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.validatePasswordResetToken("expired-token"));

        verify(tokenRepository).delete(testToken);
    }

    @Test
    @DisplayName("Test 4")
    void changeUserPassword_ValidPassword() {
        passwordResetService.changeUserPassword(testUser, "NewPass123#");

        verify(passwordEncoder).encode("NewPass123#");
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Test 5")
    void changeUserPassword_InvalidPassword() {
        assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.changeUserPassword(testUser, "weak"));
    }

    @Test
    @DisplayName("Test 6")
    void requestPasswordReset_UserNotFound() {
        when(userRepository.findByEmailAddressIgnoreCase("nonexistent@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                passwordResetService.requestPasswordReset("nonexistent@example.com"));
    }

    @Test
    @DisplayName("Test 7")
    void resetPassword_ValidInput() {
        when(tokenRepository.findByToken("valid-token"))
                .thenReturn(Optional.of(testToken));

        passwordResetService.resetPassword("valid-token", "NewPass123#");

        verify(passwordEncoder).encode("NewPass123#");
        verify(userRepository).save(testUser);
        verify(tokenRepository).delete(testToken);
    }
}
