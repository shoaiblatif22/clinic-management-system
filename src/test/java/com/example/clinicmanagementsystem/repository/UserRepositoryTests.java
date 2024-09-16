package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {
        UserEntity user = new UserEntity();
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

    }

    @Test
    public void whenFindByFirstName_thenReturnUser() {
        String firstName = "John";
        String lastName = "Doe";

        UserEntity user = userRepository.findByFirstName(firstName);

        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());

    }


}
