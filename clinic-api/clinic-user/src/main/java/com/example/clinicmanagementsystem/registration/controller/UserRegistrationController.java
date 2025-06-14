package com.example.clinicmanagementsystem.registration.controller;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.registration.entity.UserVerificationToken;
import com.example.clinicmanagementsystem.registration.events.RegistrationCompleteEvent;
import com.example.clinicmanagementsystem.registration.repository.UserRepository;
import com.example.clinicmanagementsystem.registration.repository.UserVerificationTokenRepository;
import com.example.clinicmanagementsystem.registration.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * REST controller for handling user registration and email verification.
 * All endpoints are prefixed with '/user/api/v1'.
 */
@RestController
@RequestMapping("/user/api/v1")
@RequiredArgsConstructor
public class UserRegistrationController {

    // Event publisher for handling registration events
    private final ApplicationEventPublisher eventPublisher;
    
    // Service layer for user registration business logic
    private final UserService userService;
    
    // Repository for verification token operations
    private final UserVerificationTokenRepository tokenRepository;
    
    // Repository for user data access
    private final UserRepository userRepository;

    /**
     * Registers a new user account and initiates the email verification process.
     * 
     * @param userEntity The user registration data
     * @param request The HTTP request object used to build the application URL
     * @return ResponseEntity containing the registered user and HTTP status 201 (Created) on success,
     *         or HTTP status 400 (Bad Request) if registration fails
     */
    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity userEntity, HttpServletRequest request)
    {
        try {
            // Register the user in the system (account is created but not yet enabled)
            UserEntity registeredUser = userService.registerUser(userEntity);

            // Build the application URL for the verification email
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            
            // Publish an event to send verification email asynchronously
            eventPublisher.publishEvent(new RegistrationCompleteEvent(registeredUser, appUrl));
            
            // Return the registered user with HTTP 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
            
        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request if registration fails (e.g., email already exists)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
        // Find the verification token in the database
        Optional<UserVerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }
        
        // Check if the token has expired
        UserVerificationToken userVerificationToken = optionalToken.get();
        if (userVerificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired. Please request a new verification email.");
        }
        
        // Get the user associated with the token
        UserEntity user = userVerificationToken.getUser();
        
        // Create an updated user with the account activated
        UserEntity updatedUser = UserEntity.builder()
                // Preserve all existing user data
                .id(user.getId())
                .emailAddress(user.getEmailAddress())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .addressLineOne(user.getAddressLineOne())
                .addressLineTwo(user.getAddressLineTwo())
                .townOrCity(user.getTownOrCity())
                .postcode(user.getPostcode())
                .county(user.getCounty())
                .country(user.getCountry())
                .userRole(user.getUserRole())
                // Activate the account
                .locked(false)    // Unlock the account
                .enabled(true)    // Enable the account
                .build();
        
        // Save the updated user (now activated)
        userRepository.save(updatedUser);
        
        // Delete the used verification token (one-time use)
        tokenRepository.delete(userVerificationToken);
        
        // Redirect to the success page in the frontend
        return ResponseEntity.status(302)
                .header("Location", "http://localhost:5173/verify-success")
                .build();
    }
}
