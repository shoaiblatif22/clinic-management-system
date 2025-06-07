package com.example.clinicmanagementsystem.user.resetpassword.service;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;

/**
 * Service interface for handling password reset functionality.
 */
public interface PasswordResetService {
    
    /**
     * Creates a new password reset token for the given user.
     *
     * @param user  the user requesting the password reset
     * @param token the generated token
     */
    void createPasswordResetToken(UserEntity user, String token);

    /**
     * Validates a password reset token.
     *
     * @param token the token to validate
     * @return the email address associated with the token if valid
     * @throws IllegalArgumentException if the token is invalid or expired
     */
    String validatePasswordResetToken(String token);

    /**
     * Changes a user's password.
     *
     * @param user        the user whose password should be changed
     * @param newPassword the new password
     * @throws IllegalArgumentException if the new password doesn't meet complexity requirements
     */
    void changeUserPassword(UserEntity user, String newPassword);

    /**
     * Initiates the password reset process by sending a reset link to the user's email.
     *
     * @param emailAddress the email address of the user requesting a password reset
     * @throws IllegalArgumentException if no user is found with the given email
     */
    void requestPasswordReset(String emailAddress);

    /**
     * Completes the password reset process using a valid token.
     *
     * @param token       the password reset token
     * @param newPassword the new password
     * @throws IllegalArgumentException if the token is invalid, expired, or the password is invalid
     */
    void resetPassword(String token, String newPassword);
}
