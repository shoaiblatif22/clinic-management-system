package com.example.clinicmanagementsystem;

import com.example.clinicmanagementsystem.role.Role;
import com.example.clinicmanagementsystem.user.entity.UserEntity;
import com.example.clinicmanagementsystem.user.repository.UserRepository;
import com.example.clinicmanagementsystem.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService; // Service under test

    @MockBean
    private UserRepository userRepository; // Mock the repository dependency

    @Test
    @DisplayName("_1_: Creating a user saves to the repository")
    public void testCreateUser() {
        //1. Arrange test case
        UserEntity userPOJO = new UserEntity();
        userPOJO.setFirstName("John");
        userPOJO.setLastName("Doe");
        userPOJO.setEmail("john.doe@gmail.com");
        userPOJO.setPassword("password");
        userPOJO.setRole(Role.USER);

        //2. Mock repository
        when(userRepository.save(userPOJO)).thenReturn(userPOJO);

        //3. Call Service Method
        //I need to create user here but I need to setup a rest controller first

    }

}
