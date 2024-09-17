package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.Assert.*;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    private UserEntity user;


    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Object");
        user.setPassword("test");
        user.setRole("user");
        user = userRepository.save(user);

    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("_1: When searching for a user by their first name, the user json body is returned")
    public void whenFindByFirstName_thenReturnUser() {

        Optional<UserEntity> foundUser = Optional.ofNullable(userRepository.findByFirstName("Test"));

        assertTrue(foundUser.isPresent());
        assertEquals("Test", user.getFirstName());
        assertEquals("Object", user.getLastName());

    }

    @Test
    @DisplayName("_2: When finding a valid id, the user json body is returned")
    public void whenFindById_thenReturnUser() {
        Optional<UserEntity> foundUser = userRepository.findById((int) user.getId());
        assertTrue(foundUser.isPresent());

    }

    @Test
    @DisplayName("_3: When a user is saved, the user json body is returned")
    public void whenUserIsSaved_thenReturnUser() {
        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");
        user.setFirstName("NewTest");
        user.setLastName("Object");
        user.setPassword("test");
        user.setRole("user");
        userRepository.save(user);

        assertNotNull(user.getId());
        assertEquals("NewTest", user.getFirstName());
        assertEquals("Object", user.getLastName());
        assertEquals("test", user.getPassword());
        assertEquals("user", user.getRole());
    }


}
