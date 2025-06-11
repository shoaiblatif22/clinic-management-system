package com.example.clinicmanagementsystem.password_reset.events;


import com.example.clinicmanagementsystem.registration.entity.UserEntity;

/**
 * Event triggered when a password reset is requested.
 * @param userEntity The user requesting the password reset
 * @param token The password reset token
 * @param appUrl The base URL of the application for constructing reset links
 */
public record PasswordResetEvent(UserEntity userEntity, String token, String appUrl) {
    public PasswordResetEvent(UserEntity userEntity, String token) {
        this(userEntity, token, null);
    }
}
