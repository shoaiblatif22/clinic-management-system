package com.example.clinicmanagementsystem.service;


import com.example.clinicmanagementsystem.model.User;
import com.example.clinicmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    //Arrange
    //This is what we're inserting in our function

    //Act
    //This is the actual function

    //Assert
    //Did we see that we're inserting our function?

    @Mock
    public UserRepository userRepository;

    @InjectMocks
    public UserService userService;

    @Mock
    public User user;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); //you always need to do this for initialization of//
        //mocks when using the @Mock annotation//
    }
    @Test
    public void givenUser_whenSaveUser_thenUserIsSaved() {
        // Arrange
        String testName = "Test User";
        String testEmail = "test@example.com";

        // Set up mock behavior for the User model


        // Act


        // Assert

    }

}
