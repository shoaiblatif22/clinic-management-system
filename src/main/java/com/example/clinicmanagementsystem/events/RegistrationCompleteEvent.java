package com.example.clinicmanagementsystem.events;

import com.example.clinicmanagementsystem.entity.ClinicAppUser;

public record RegistrationCompleteEvent(ClinicAppUser clinicAppUser, String appUrl) {
}
