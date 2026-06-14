package com.example.clinicmanagementsystem.password_reset.service;

import com.example.clinicmanagementsystem.password_reset.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.password_reset.events.PasswordResetEvent;
import com.example.clinicmanagementsystem.password_reset.repository.PasswordResetTokenRepository;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock PasswordResetTokenRepository tokenRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock ApplicationEventPublisher eventPublisher;

    @InjectMocks
    PasswordResetServiceImpl service;

    private UserEntity enabledUser;
    private UserEntity disabledUser;

    @BeforeEach
    void setUp() {
        enabledUser = UserEntity.builder()
                .emailAddress("enabled@example.com")
                .password("old")
                .locked(false)
                .enabled(true)
                .build();

        disabledUser = UserEntity.builder()
                .emailAddress("disabled@example.com")
                .password("old")
                .locked(false)
                .enabled(false)
                .build();
    }

    // --- createPasswordResetToken ---

    @Test
    void createPasswordResetToken_noExisting_savesNewToken() {
        when(tokenRepository.findByUser(enabledUser)).thenReturn(Optional.empty());

        service.createPasswordResetToken(enabledUser, "token-abc");

        verify(tokenRepository).save(any(PasswordResetToken.class));
    }

    @Test
    void createPasswordResetToken_existing_updatesToken() {
        PasswordResetToken existing = new PasswordResetToken("old-token", enabledUser);
        when(tokenRepository.findByUser(enabledUser)).thenReturn(Optional.of(existing));

        service.createPasswordResetToken(enabledUser, "new-token");

        assertEquals("new-token", existing.getToken());
        verify(tokenRepository).save(existing);
    }

    // --- validatePasswordResetToken ---

    @Test
    void validatePasswordResetToken_invalidToken_throws() {
        when(tokenRepository.findByToken("bad")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.validatePasswordResetToken("bad"));
    }

    @Test
    void validatePasswordResetToken_expiredToken_deletesAndThrows() {
        PasswordResetToken expired = new PasswordResetToken("tok", enabledUser);
        expired.setExpiryDate(new Date(System.currentTimeMillis() - 1000));
        when(tokenRepository.findByToken("tok")).thenReturn(Optional.of(expired));

        assertThrows(IllegalArgumentException.class, () -> service.validatePasswordResetToken("tok"));
        verify(tokenRepository).delete(expired);
    }

    @Test
    void validatePasswordResetToken_validToken_returnsEmail() {
        PasswordResetToken valid = new PasswordResetToken("tok", enabledUser);
        when(tokenRepository.findByToken("tok")).thenReturn(Optional.of(valid));

        assertEquals("enabled@example.com", service.validatePasswordResetToken("tok"));
    }

    // --- changeUserPassword ---

    @Test
    void changeUserPassword_validPassword_savesWithEncodedPassword() {
        when(passwordEncoder.encode("NewPass123#")).thenReturn("encoded");

        service.changeUserPassword(enabledUser, "NewPass123#");

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertEquals("encoded", captor.getValue().getPassword());
    }

    @Test
    void changeUserPassword_weakPassword_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> service.changeUserPassword(enabledUser, "weak"));
        verifyNoInteractions(userRepository);
    }

    // --- requestPasswordReset ---

    @Test
    void requestPasswordReset_knownEnabledUser_publishesEvent() {
        when(userRepository.findByEmailAddressIgnoreCase("enabled@example.com"))
                .thenReturn(Optional.of(enabledUser));
        when(tokenRepository.findByUser(enabledUser)).thenReturn(Optional.empty());

        service.requestPasswordReset("enabled@example.com");

        verify(eventPublisher).publishEvent(any(PasswordResetEvent.class));
    }

    @Test
    void requestPasswordReset_unknownEmail_isNoOp() {
        when(userRepository.findByEmailAddressIgnoreCase("unknown@example.com"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.requestPasswordReset("unknown@example.com"));
        verifyNoInteractions(eventPublisher);
    }

    @Test
    void requestPasswordReset_disabledUser_isNoOp() {
        when(userRepository.findByEmailAddressIgnoreCase("disabled@example.com"))
                .thenReturn(Optional.of(disabledUser));

        assertDoesNotThrow(() -> service.requestPasswordReset("disabled@example.com"));
        verifyNoInteractions(eventPublisher);
    }

    // --- resetPassword ---

    @Test
    void resetPassword_weakPassword_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> service.resetPassword("tok", "weak"));
        verifyNoInteractions(tokenRepository);
    }

    @Test
    void resetPassword_invalidToken_throws() {
        when(tokenRepository.findByToken("bad")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.resetPassword("bad", "NewPass123#"));
    }

    @Test
    void resetPassword_expiredToken_throws() {
        PasswordResetToken expired = new PasswordResetToken("tok", enabledUser);
        expired.setExpiryDate(new Date(System.currentTimeMillis() - 1000));
        when(tokenRepository.findByToken("tok")).thenReturn(Optional.of(expired));

        assertThrows(IllegalArgumentException.class,
                () -> service.resetPassword("tok", "NewPass123#"));
    }

    @Test
    void resetPassword_valid_savesEncodedPasswordAndDeletesToken() {
        PasswordResetToken valid = new PasswordResetToken("tok", enabledUser);
        when(tokenRepository.findByToken("tok")).thenReturn(Optional.of(valid));
        when(passwordEncoder.encode("NewPass123#")).thenReturn("encoded");

        service.resetPassword("tok", "NewPass123#");

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertEquals("encoded", captor.getValue().getPassword());
        verify(tokenRepository).delete(valid);
    }
}
