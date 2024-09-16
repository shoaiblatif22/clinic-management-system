package com.example.clinicmanagementsystem.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UserModelTests {
    @Test
    void testGettersAndSetters() {
        UserModel userModel = new UserModel();
        userModel.setFirstName("TestFN");
        userModel.setLastName("TestLN");
        userModel.setEmail("test@test.com");
        userModel.setPassword("TestPW");
        userModel.setRole("USER");

        assertEquals("TestFN", userModel.getFirstName());
        assertEquals("TestLN", userModel.getLastName());
        assertEquals("test@test.com", userModel.getEmail());
        assertEquals("TestPW", userModel.getPassword());
        assertEquals("USER", userModel.getRole());

    }
    @Test
    @Disabled
    void testInvalidEmail() {
        UserModel userModel = new UserModel();
        assertThrows(IllegalArgumentException.class, () -> {
            userModel.setEmail("invalidemail");
        });
    }
}
