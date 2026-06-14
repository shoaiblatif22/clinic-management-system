package com.example.clinicmanagementsystem.login.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;

public record LoginEvent(UserEntity userEntity) {
}