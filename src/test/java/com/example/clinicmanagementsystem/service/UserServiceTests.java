package com.example.clinicmanagementsystem.service;


import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    //Arrange
    //This is what we're inserting in our function

    //Act
    //This is the actual function

    //Assert
    //Did we see that we're inserting our function?

    @Test
    @DisplayName("_4: When searching for a first name, " +
            "if there are more than one users with the same first name, " +
            "return a list of users")
    public void whenFindByFirstName_thenReturnUsers() {
    }
}
