package com.example.clinicmanagementsystem.login.service;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // must be ≥32 bytes for HS256
        ReflectionTestUtils.setField(jwtService, "secretKey", "test-secret-key-that-is-long-enough-32bytes");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L); // 1 hour

        user = UserEntity.builder()
                .emailAddress("test@example.com")
                .password("encoded")
                .locked(false)
                .enabled(true)
                .build();
    }

    @Test
    void generateToken_returnsNonNullToken() {
        assertNotNull(jwtService.generateToken(user));
    }

    @Test
    void extractUsername_returnsEmailFromToken() {
        String token = jwtService.generateToken(user);
        assertEquals("test@example.com", jwtService.extractUsername(token));
    }

    @Test
    void isTokenValid_validTokenAndMatchingUser_returnsTrue() {
        String token = jwtService.generateToken(user);
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void isTokenValid_wrongUser_returnsFalse() {
        String token = jwtService.generateToken(user);
        UserEntity other = UserEntity.builder()
                .emailAddress("other@example.com")
                .password("encoded")
                .locked(false)
                .enabled(true)
                .build();
        assertFalse(jwtService.isTokenValid(token, other));
    }

    @Test
    void isTokenValid_expiredToken_returnsFalse() {
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1000L);
        String token = jwtService.generateToken(user);
        assertFalse(jwtService.isTokenValid(token, user));
    }
}
