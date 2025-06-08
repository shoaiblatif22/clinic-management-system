package com.example.clinicmanagementsystem.user.registration.service;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.user.registration.model.UserRole;
import com.example.clinicmanagementsystem.user.registration.repository.UserRepository;
import com.example.clinicmanagementsystem.user.registration.repository.UserVerificationTokenRepository;
import com.example.clinicmanagementsystem.user.registration.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user accounts and authentication.
 * Handles user registration, loading user details, and related operations.
 */
@Slf4j

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    // Error messages
    private static final String EMAIL_ALREADY_EXISTS = "Email is already registered";
    private static final String INVALID_EMAIL = "Email is required and cannot be empty";
    private static final String INVALID_PASSWORD = "Password is required and cannot be empty";
    private static final String USER_NOT_FOUND = "User not found with email: %s";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final UserVerificationTokenRepository tokenRepository;

    /**
     * Loads a user by their email address (username).
     *
     * @param email the email address of the user to load
     * @return UserDetails containing the user's information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);
        return userRepository.findByEmailAddressIgnoreCase(email)
                .orElseThrow(() -> {
                    String errorMessage = String.format(USER_NOT_FOUND, email);
                    log.warn(errorMessage);
                    return new UsernameNotFoundException(errorMessage);
                });
    }

    /**
     * Registers a new user with the provided user details.
     * The user account will be created in a locked and disabled state until email verification.
     *
     * @param userEntity the user to register
     * @return the registered user with encoded password
     * @throws IllegalArgumentException if the email is already in use or required fields are missing
     */
    @Transactional
    public UserEntity registerUser(UserEntity userEntity) {
        log.info("Registering new user with email: {}", userEntity.getEmailAddress());

        // Validate input
        validateUserRegistration(userEntity);

        // Check if email already exists
        if (userRepository.findByEmailAddressIgnoreCase(userEntity.getEmailAddress()).isPresent()) {
            log.warn("Registration failed - email already exists: {}", userEntity.getEmailAddress());
            throw new IllegalArgumentException(EMAIL_ALREADY_EXISTS);
        }

        // Create and save new user
        UserEntity newUser = buildNewUser(userEntity);
        UserEntity savedUser = userRepository.save(newUser);

        log.info("Successfully registered user with ID: {}", savedUser.getId());
        return savedUser;
    }

    /**
     * Validates user registration data.
     *
     * @param userEntity the user to validate
     * @return userEntity
     * @throws IllegalArgumentException if validation fails
     */
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

    /**
     * Builds a new UserEntity with the provided details.
     * Applies security settings like password encoding and account status.
     *
     * @param userEntity the source user data
     * @return a new UserEntity ready for persistence
     */
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
                .locked(true)     // Lock account until email verification
                .enabled(false)    // Disable until email verification
                .build();
    }
}
