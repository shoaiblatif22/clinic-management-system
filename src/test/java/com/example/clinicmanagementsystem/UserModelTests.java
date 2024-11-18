package com.example.clinicmanagementsystem;

import com.example.clinicmanagementsystem.user.model.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class UserModelTests {
    @Test
    @DisplayName("_1_:Test getters and setters for userModel")
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
}
