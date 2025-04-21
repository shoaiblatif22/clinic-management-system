package com.example.clinicmanagementsystem.user.registration.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCompleteListener {

    @EventListener
    public void handleRegistrationCompleteEvent(RegistrationCompleteEvent event) {
        if (event.clinicAppUser() != null) {
            System.out.println("User registered: " + event.clinicAppUser().getEmailAddress());
            System.out.println("Verification URL: " + event.appUrl());
        } else {
            System.out.println("Error: ClinicAppUser is null in the event.");
        }
    }
}
