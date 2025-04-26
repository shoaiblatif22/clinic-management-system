package com.example.clinicmanagementsystem.user.resetpassword.controller;

import com.example.clinicmanagementsystem.user.registration.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.user.resetpassword.events.PasswordResetEvent;
import com.example.clinicmanagementsystem.user.resetpassword.service.PasswordResetService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationEventPublisher;


@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
 

    // Endpoint to request password reset (send email with token)
    @PostMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    public void requestPasswordReset(@RequestParam String emailAddress, HttpServletRequest request) {
        ClinicAppUser user = passwordResetService.createPasswordResetToken(emailAddress);
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        eventPublisher.publishEvent(new PasswordResetEvent(user, appUrl));
    }

    // Endpoint to reset the password using the token
    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
    }
}
