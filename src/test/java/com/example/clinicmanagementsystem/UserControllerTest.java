package com.example.clinicmanagementsystem;

import com.example.clinicmanagementsystem.controller.UserController;
import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @Mock
    private UserService userService; // Mock the service

    @InjectMocks
    private UserController userController; // The controller under test

    // Initialize mocks before each test
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllUsers() throws Exception {
        assertNotNull(userController.findAllUsers());
    }

    @Test
    void testGetUserById() {
        // 1. Mock service response
        int id = 1;
        UserModel mockUser = new UserModel(id, "John", "Doe", "john.doe@example.com", "password", "USER", false, true);
        when(userService.getUserById(id))
                .thenReturn(Optional.of(mockUser));

        // 2. Call the controller method directly
        ResponseEntity<UserModel> response = userController.getUserById(id); // Assuming you have this method

        // 3. Assert the response
        assertEquals(HttpStatus.OK.is2xxSuccessful(), response.getStatusCode().is2xxSuccessful());
        assertEquals(1, response.getBody().getId());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void save() throws Exception {
        // 1. Mock service response
        int id = 1;
        UserModel mockUser = new UserModel(id, "John", "Doe", "john.doe@example.com", "password", "USER", false, true);
        when(userService.saveUser(any(UserEntity.class))).thenReturn(mockUser);

        // 2. Create a UserEntity object
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPassword("password");
        userEntity.setRole("USER");

        // 3. Call the controller method
        UserModel response = userController.save(userEntity);

        // 4. Assertions
        verify(userService, times(1)).saveUser(any(UserEntity.class));
        assertEquals(mockUser, response);
        assert(HttpStatus.OK.is2xxSuccessful());
    }
}