package com.example.clinicmanagementsystem.registration.model;

import lombok.Getter;

@Getter
public enum UserGender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    PREFER_NOT_TO_SAY("Prefer not to say"),
    UNKNOWN("Unknown");

    private final String displayName;

    UserGender(String displayName) {
        this.displayName = displayName;
    }
}
