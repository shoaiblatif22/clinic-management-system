package com.example.clinicmanagementsystem.registration.controller;

import com.example.clinicmanagementsystem.registration.dto.UserRegistrationRequest;
import com.example.clinicmanagementsystem.registration.dto.UserRegistrationResponse;
import com.example.clinicmanagementsystem.registration.dto.UserResponse;
import com.example.clinicmanagementsystem.registration.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling user registration and email verification.
 * All endpoints are prefixed with '/user/api/v1'.
 */
@RestController
@RequestMapping("/user/api/v1")
@RequiredArgsConstructor
public class UserRegistrationController {

    // Service layer for user registration business logic
    private final UserService userService;

    /**
     * Registers a new user account and initiates the email verification process.
     *
     * @param request The user registration data
     * @param httpRequest The HTTP request object used to build the application URL
     * @return ResponseEntity containing the registered user and HTTP status 201 (Created) on success,
     *         or HTTP status 400 (Bad Request) if registration fails
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request,
                                                                 HttpServletRequest httpRequest) {
        try {
            // Build the application URL for the verification email
            String appUrl = httpRequest.getScheme() + "://"
                    + httpRequest.getServerName() + ":"
                    + httpRequest.getServerPort();

            // The service registers the user and publishes the verification event
            UserRegistrationResponse registeredUser = userService.registerUser(request, appUrl);

            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            // e.g. email already exists or required fields are missing
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Verifies a user's email address using the provided verification token.
     *
     * @param token The verification token sent to the user's email
     * @return Redirects to the success page if verification is successful,
     *         or returns an error message if the token is invalid or expired
     */
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        try {
            userService.verifyEmail(token);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "http://localhost:5173/verify-success")
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}