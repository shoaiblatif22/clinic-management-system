package com.example.clinicmanagementsystem.user.registration.events;

import com.example.clinicmanagementsystem.user.registration.entity.ClinicAppUser;

public record RegistrationCompleteEvent(ClinicAppUser clinicAppUser, String appUrl) {
}
