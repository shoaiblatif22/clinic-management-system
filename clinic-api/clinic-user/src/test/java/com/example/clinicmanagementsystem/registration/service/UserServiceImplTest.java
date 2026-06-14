package com.example.clinicmanagementsystem.registration.service;

import com.example.clinicmanagementsystem.registration.dto.UserRegistrationRequest;
import com.example.clinicmanagementsystem.registration.dto.UserRegistrationResponse;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.entity.UserVerificationToken;
import com.example.clinicmanagementsystem.registration.model.UserRole;
import com.example.clinicmanagementsystem.registration.repository.UserRepository;
import com.example.clinicmanagementsystem.registration.repository.UserVerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock ApplicationEventPublisher eventPublisher;
    @Mock UserVerificationTokenRepository tokenRepository;
    @Mock JavaMailSender mailSender;

    @InjectMocks
    UserServiceImpl userService;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = UserEntity.builder()
                .emailAddress("john@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .userRole(UserRole.USER)
                .locked(false)
                .enabled(false)
                .build();
    }

    // --- validateUserRegistration ---

    @Test
    void validateUserRegistration_nullUser_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.validateUserRegistration(null));
        assertEquals("User cannot be null", ex.getMessage());
    }

    @Test
    void validateUserRegistration_nullEmail_throws() {
        testUser.setEmailAddress(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.validateUserRegistration(testUser));
        assertEquals("Email is required and cannot be empty", ex.getMessage());
    }

    @Test
    void validateUserRegistration_blankEmail_throws() {
        testUser.setEmailAddress("  ");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.validateUserRegistration(testUser));
        assertEquals("Email is required and cannot be empty", ex.getMessage());
    }

    @Test
    void validateUserRegistration_nullPassword_throws() {
        testUser.setPassword(null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.validateUserRegistration(testUser));
        assertEquals("Password is required and cannot be empty", ex.getMessage());
    }

    @Test
    void validateUserRegistration_blankPassword_throws() {
        testUser.setPassword("");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.validateUserRegistration(testUser));
        assertEquals("Password is required and cannot be empty", ex.getMessage());
    }

    @Test
    void validateUserRegistration_validUser_returnsUser() {
        UserEntity result = userService.validateUserRegistration(testUser);
        assertSame(testUser, result);
    }

    // --- registerUser(UserEntity) ---

    @Test
    void registerUser_entity_newUser_savesLockedAndDisabled() {
        when(userRepository.findByEmailAddressIgnoreCase(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserEntity saved = userService.registerUser(testUser);

        assertTrue(saved.getLocked());
        assertFalse(saved.getEnabled());
        assertEquals("encoded", saved.getPassword());
    }

    @Test
    void registerUser_entity_duplicateEmail_throws() {
        when(userRepository.findByEmailAddressIgnoreCase(any())).thenReturn(Optional.of(testUser));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(testUser));
        assertEquals("Email is already registered", ex.getMessage());
    }

    // --- registerUser(UserRegistrationRequest, appUrl) ---

    @Test
    void registerUser_request_success_publishesEventAndReturnsResponse() {
        UserRegistrationRequest req = UserRegistrationRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .emailAddress("jane@example.com")
                .password("secret")
                .build();

        when(userRepository.findByEmailAddressIgnoreCase(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenAnswer(i -> {
            UserEntity u = i.getArgument(0);
            return u.toBuilder().id(1L).build();
        });

        UserRegistrationResponse response = userService.registerUser(req, "http://app");

        verify(eventPublisher).publishEvent(any(Object.class));
        assertEquals("jane@example.com", response.getEmailAddress());
        assertTrue(response.getLocked());
        assertFalse(response.getEnabled());
    }

    @Test
    void registerUser_request_duplicateEmail_throws() {
        UserRegistrationRequest req = UserRegistrationRequest.builder()
                .emailAddress("john@example.com")
                .password("secret")
                .firstName("John")
                .build();

        when(userRepository.findByEmailAddressIgnoreCase(any())).thenReturn(Optional.of(testUser));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(req, "http://app"));
    }

    @Test
    void registerUser_request_nullRequest_throws() {
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser((UserRegistrationRequest) null, "http://app"));
    }

    // --- verifyEmail ---

    @Test
    void verifyEmail_invalidToken_throws() {
        when(tokenRepository.findByToken("bad")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.verifyEmail("bad"));
        assertEquals("Invalid verification token.", ex.getMessage());
    }

    @Test
    void verifyEmail_expiredToken_throws() {
        UserVerificationToken token = UserVerificationToken.builder()
                .token("expired")
                .user(testUser)
                .expiryDate(LocalDateTime.now().minusHours(1))
                .build();
        when(tokenRepository.findByToken("expired")).thenReturn(Optional.of(token));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.verifyEmail("expired"));
        assertTrue(ex.getMessage().contains("expired"));
    }

    @Test
    void verifyEmail_validToken_enablesUserDeletesTokenSendsEmail() {
        UserVerificationToken token = UserVerificationToken.builder()
                .token("valid")
                .user(testUser)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();
        when(tokenRepository.findByToken("valid")).thenReturn(Optional.of(token));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        userService.verifyEmail("valid");

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertTrue(captor.getValue().getEnabled());
        assertFalse(captor.getValue().getLocked());

        verify(tokenRepository).delete(token);
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
