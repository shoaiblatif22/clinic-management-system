package com.example.clinicmanagementsystem.login.controller;


import com.example.clinicmanagementsystem.login.dto.LoginRequest;
import com.example.clinicmanagementsystem.login.dto.LoginResponse;
import com.example.clinicmanagementsystem.login.mapper.CustomUserMapper;
import com.example.clinicmanagementsystem.login.service.JwtService;
import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user authentication and login operations.
 */
@RestController
@RequestMapping("/user/api/v1")
@RequiredArgsConstructor
public class LoginController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserMapper userMapper;

    /**
     * Authenticates a user and returns a JWT token upon successful authentication.
     *
     * @param loginRequest the login request containing email and password
     * @return ResponseEntity containing the login response with JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Get the authenticated user
            UserEntity userEntity = (UserEntity) authentication.getPrincipal();
            
            // Generate JWT token
            String token = jwtService.generateToken(userEntity);

            // Return the login response
            return ResponseEntity.ok(userMapper.toLoginResponse(userEntity, token));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

//@RestController
//@RequestMapping("/user/api/v1")
//@RequiredArgsConstructor
//public class LoginController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//    private final CustomUserMapper userMapper;
//
//    /**
//     * Endpoint for user login
//     *
//     * @param loginRequest The login request containing email and password
//     * @return A response entity containing the login response with JWT token
//     */
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
//        try {
//            // Authenticate the user
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(),
//                            loginRequest.getPassword()
//                    )
//            );
//
//            // Set the authentication in the security context
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Get the authenticated user
//            UserEntity userEntity = (UserEntity) authentication.getPrincipal();
//
//            // Generate JWT token
//            String token = jwtService.generateToken(userEntity);
//
//            // Create and return the login response
//            LoginResponse loginResponse = userMapper.toLoginResponse(userEntity, token);
//            return ResponseEntity.ok(loginResponse);
//
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//}
