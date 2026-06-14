package com.example.clinicmanagementsystem.registration.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;

public record EmailVerifiedEvent(UserEntity userEntity) {
}