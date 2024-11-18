package com.example.clinicmanagementsystem;

import com.example.clinicmanagementsystem.user.controller.UserController;
import com.example.clinicmanagementsystem.user.entity.UserEntity;
import com.example.clinicmanagementsystem.user.model.UserModel;
import com.example.clinicmanagementsystem.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("_1_: Test to update user")
    void updateUser() throws Exception {
        int id = 1;
        UserModel existingUser = new UserModel(id,"John", "Doe", "john.doe@example.com", "password", "USER", false, true );
        UserModel updatedUser = new UserModel(id, "Jane", "Doe", "jane.doe@example.com", "newPassword", "USER", false, true);

        when(userService.update(id, updatedUser)).thenReturn(updatedUser);

        UserModel result = userController.update(id, updatedUser);

        verify(userService, times(1)).update(id, updatedUser);
        assertNotNull(result);
    }

    @Test
    @DisplayName("_2_: Test to get user by id")
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
    @DisplayName("_3_: Test to save user with a dynamic role")
    void save() throws Exception {
        // 1. Mock service response
        int id = 1;
        // Set the desired role for this test
        UserModel mockUser = new UserModel(id, "John", "Doe", "john.doe@example.com", "password", "USER", false, true);

        // Mock service behavior
        when(userService.saveUser(any(UserEntity.class))).thenReturn(mockUser);

        // 2. Create a UserEntity object
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPassword("password");

        // 3. Call the controller method
        UserModel response = userController.save(userEntity);

        // 4. Assertions
        verify(userService, times(1)).saveUser(any(UserEntity.class));
        assertNotNull(response);
        assertEquals(mockUser, response);
    }
}
