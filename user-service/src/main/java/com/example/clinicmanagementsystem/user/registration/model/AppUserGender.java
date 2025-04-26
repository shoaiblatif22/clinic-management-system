package com.example.clinicmanagementsystem.user.registration.model;

import lombok.Getter;

@Getter
public enum AppUserGender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    PREFER_NOT_TO_SAY("Prefer not to say"),
    UNKNOWN("Unknown");

    private final String displayName;

    AppUserGender(String displayName) {
        this.displayName = displayName;
    }
}
