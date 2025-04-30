package com.example.clinicmanagementsystem.user.registration.controller;

import com.example.clinicmanagementsystem.user.registration.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.user.registration.entity.VerificationToken;
import com.example.clinicmanagementsystem.user.registration.events.RegistrationCompleteEvent;
import com.example.clinicmanagementsystem.user.registration.repository.AppUserRepository;
import com.example.clinicmanagementsystem.user.registration.repository.VerificationTokenRepository;
import com.example.clinicmanagementsystem.user.registration.service.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final ApplicationEventPublisher eventPublisher;
    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<ClinicAppUser> registerUser(@RequestBody ClinicAppUser clinicAppUser, HttpServletRequest request, Errors errors) {
        try {
            ClinicAppUser registeredUser = appUserService.registerUser(clinicAppUser);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            eventPublisher.publishEvent(new RegistrationCompleteEvent(registeredUser, appUrl));

            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}


