package com.example.clinicmanagementsystem.registration.service;

import com.example.clinicmanagementsystem.registration.dto.UserRegistrationRequest;
import com.example.clinicmanagementsystem.registration.dto.UserRegistrationResponse;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.entity.UserVerificationToken;
import com.example.clinicmanagementsystem.registration.events.RegistrationCompleteEvent;
import com.example.clinicmanagementsystem.registration.model.UserRole;
import com.example.clinicmanagementsystem.registration.repository.UserRepository;
import com.example.clinicmanagementsystem.registration.repository.UserVerificationTokenRepository;
import com.example.clinicmanagementsystem.registration.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Default implementation of {@link UserService}.
 * Holds all the user registration and email verification business logic.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Error messages
    private static final String EMAIL_ALREADY_EXISTS = "Email is already registered";
    private static final String INVALID_EMAIL = "Email is required and cannot be empty";
    private static final String INVALID_PASSWORD = "Password is required and cannot be empty";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final UserVerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public UserEntity registerUser(UserEntity userEntity) {
        log.info("Registering new user with email: {}", userEntity.getEmailAddress());

        validateUserRegistration(userEntity);

        if (userRepository.findByEmailAddressIgnoreCase(userEntity.getEmailAddress()).isPresent()) {
            log.warn("Registration failed - email already exists: {}", userEntity.getEmailAddress());
            throw new IllegalArgumentException(EMAIL_ALREADY_EXISTS);
        }

        UserEntity newUser = buildNewUser(userEntity);
        UserEntity savedUser = userRepository.save(newUser);

        log.info("Successfully registered user with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    @Transactional
    public UserRegistrationResponse registerUser(UserRegistrationRequest request, String appUrl) {
        validateUserRegistrationRequest(request);

        log.info("Registering new user with email: {}", request.getEmailAddress());

        if (userRepository.findByEmailAddressIgnoreCase(request.getEmailAddress()).isPresent()) {
            log.warn("Registration failed - email already exists: {}", request.getEmailAddress());
            throw new IllegalArgumentException(EMAIL_ALREADY_EXISTS);
        }

        UserEntity newUser = buildNewUserFromRequest(request);
        UserEntity savedUser = userRepository.save(newUser);

        // Publish event to send verification email (publisher lives in the service layer)
        eventPublisher.publishEvent(new RegistrationCompleteEvent(savedUser, appUrl));

        log.info("Successfully registered user with ID: {}", savedUser.getId());
        return mapToResponse(savedUser);
    }

    @Override
    public UserEntity validateUserRegistration(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (StringUtils.isBlank(userEntity.getEmailAddress())) {
            throw new IllegalArgumentException(INVALID_EMAIL);
        }
        if (StringUtils.isBlank(userEntity.getPassword())) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
        return userEntity;
    }

    @Override
    public void validateUserRegistrationRequest(UserRegistrationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (StringUtils.isBlank(request.getEmailAddress())) {
            throw new IllegalArgumentException(INVALID_EMAIL);
        }
        if (StringUtils.isBlank(request.getPassword())) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        log.info("Verifying email with token: {}", token);

        Optional<UserVerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            log.warn("Verification failed - invalid token: {}", token);
            throw new IllegalArgumentException("Invalid verification token.");
        }

        UserVerificationToken userVerificationToken = optionalToken.get();
        if (userVerificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("Verification failed - token expired: {}", token);
            throw new IllegalArgumentException("Token expired. Please request a new verification email.");
        }

        UserEntity user = userVerificationToken.getUser();

        UserEntity updatedUser = user.toBuilder()
                .locked(false)    // Unlock the account
                .enabled(true)    // Enable the account
                .build();

        userRepository.save(updatedUser);
        tokenRepository.delete(userVerificationToken);

        log.info("Email verified for user - email: {}, role: {}, account enabled: true",
                user.getEmailAddress(), user.getUserRole());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmailAddress());
        message.setSubject("Welcome to the Clinic Management System");
        message.setText("Hi " + user.getFirstName() + ",\n\n"
                + "Your email has been verified and your account is now active.\n"
                + "You can now log in and start using the Clinic Management System.\n\n"
                + "Welcome aboard!");
        mailSender.send(message);
        log.info("Welcome email sent to: {}", user.getEmailAddress());
    }

    private UserEntity buildNewUser(UserEntity userEntity) {
        return UserEntity.builder()
                .emailAddress(userEntity.getEmailAddress().toLowerCase().trim())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .dateOfBirth(userEntity.getDateOfBirth())
                .gender(userEntity.getGender())
                .phoneNumber(userEntity.getPhoneNumber())
                .addressLineOne(userEntity.getAddressLineOne())
                .addressLineTwo(userEntity.getAddressLineTwo())
                .townOrCity(userEntity.getTownOrCity())
                .postcode(userEntity.getPostcode())
                .county(userEntity.getCounty())
                .country(userEntity.getCountry())
                .userRole(userEntity.getUserRole() != null ? userEntity.getUserRole() : UserRole.USER)
                .locked(true)
                .enabled(false)
                .build();
    }

    private UserEntity buildNewUserFromRequest(UserRegistrationRequest request) {
        return UserEntity.builder()
                .emailAddress(request.getEmailAddress().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .addressLineOne(request.getAddressLineOne())
                .addressLineTwo(request.getAddressLineTwo())
                .townOrCity(request.getTownOrCity())
                .postcode(request.getPostcode())
                .county(request.getCounty())
                .country(request.getCountry())
                .userRole(request.getUserRole() != null ? request.getUserRole() : UserRole.USER)
                .locked(true)
                .enabled(false)
                .build();
    }

    private UserRegistrationResponse mapToResponse(UserEntity userEntity) {
        return UserRegistrationResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .dateOfBirth(userEntity.getDateOfBirth())
                .gender(userEntity.getGender())
                .phoneNumber(userEntity.getPhoneNumber())
                .emailAddress(userEntity.getEmailAddress())
                .addressLineOne(userEntity.getAddressLineOne())
                .addressLineTwo(userEntity.getAddressLineTwo())
                .townOrCity(userEntity.getTownOrCity())
                .postcode(userEntity.getPostcode())
                .county(userEntity.getCounty())
                .country(userEntity.getCountry())
                .userRole(userEntity.getUserRole())
                .locked(userEntity.getLocked())
                .enabled(userEntity.getEnabled())
                .build();
    }
}