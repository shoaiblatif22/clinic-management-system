package com.example.clinicmanagementsystem.user.resetpassword.events;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;

public record PasswordResetEvent(UserEntity userEntity, String appUrl) {
}
