package com.example.clinicmanagementsystem.user.resetpassword.events;

import com.example.clinicmanagementsystem.user.registration.entity.ClinicAppUser;

public record PasswordResetEvent(ClinicAppUser clinicAppUser, String appUrl) {
}
