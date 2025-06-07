package com.example.clinicmanagementsystem.user.registration.events;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;

public record RegistrationCompleteEvent(UserEntity userEntity, String appUrl) {
}
