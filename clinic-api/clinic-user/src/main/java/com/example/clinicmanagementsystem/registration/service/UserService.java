package com.example.clinicmanagementsystem.registration.service;

import com.example.clinicmanagementsystem.registration.dto.UserRegistrationRequest;
import com.example.clinicmanagementsystem.registration.dto.UserRegistrationResponse;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;

/**
 * Service contract for managing user accounts.
 * Handles user registration, validation and email verification.
 */
public interface UserService {

    /**
     * Registers a new user, persists it in a locked/disabled state and
     * publishes the event that triggers the verification email.
     *
     * @param request the user registration data
     * @param appUrl  the application URL used to build the verification link
     * @return the registered user as a response DTO
     * @throws IllegalArgumentException if the email is already in use or required fields are missing
     */
    UserRegistrationResponse registerUser(UserRegistrationRequest request, String appUrl);

    /**
     * Registers a new user from an entity (used by tests and internal callers).
     *
     * @param userEntity the user to register
     * @return the persisted user entity
     * @throws IllegalArgumentException if the email is already in use or required fields are missing
     */
    UserEntity registerUser(UserEntity userEntity);

    /**
     * Validates the mandatory fields of a user entity.
     *
     * @param userEntity the user to validate
     * @return the same entity if valid
     * @throws IllegalArgumentException if validation fails
     */
    UserEntity validateUserRegistration(UserEntity userEntity);

    /**
     * Validates the mandatory fields of a registration request.
     *
     * @param request the registration request to validate
     * @throws IllegalArgumentException if validation fails
     */
    void validateUserRegistrationRequest(UserRegistrationRequest request);

    /**
     * Verifies a user's email address using the provided verification token.
     *
     * @param token the verification token sent to the user's email
     * @throws IllegalArgumentException if the token is invalid or expired
     */
    void verifyEmail(String token);
}