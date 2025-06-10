package com.example.clinicalmanagementsystem.unit_tests.UnitTestRegistration;

import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.user.registration.entity.UserVerificationToken;
import com.example.clinicmanagementsystem.user.registration.model.UserRole;
import com.example.clinicmanagementsystem.user.registration.repository.UserRepository;
import com.example.clinicmanagementsystem.user.registration.repository.UserVerificationTokenRepository;
import com.example.clinicmanagementsystem.user.registration.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceUnitTests {
    @Mock
    UserRepository userRepository;
    @Mock
    UserVerificationTokenRepository userVerificationTokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    UserEntity testUser;
    UserVerificationToken userVerificationToken;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testUser.setGender("Male");
        testUser.setPhoneNumber("01234567890");
        testUser.setEmailAddress("john.doe@example.com");
        testUser.setAddressLineOne("123 Main Street");
        testUser.setAddressLineTwo("Apt 4B");
        testUser.setTownOrCity("Leeds");
        testUser.setPostcode("LS1 1AA");
        testUser.setCounty("West Yorkshire");
        testUser.setCountry("UK");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setUserRole(UserRole.USER);
    }

    @AfterEach
    void tearDown() {
        testUser = null;
    }

    @Test
    @DisplayName("Test 1: When validating user information during user registration, return users")
    void test_valid_user_for_validUserRegistration() {
        // Act
        UserEntity validatedUser = userService.validateUserRegistration(testUser);
        //Arrange (asserting user is not null)
        Assertions.assertNotNull(validatedUser);
        Assertions.assertEquals(validatedUser.getEmailAddress(), testUser.getEmailAddress());
    }

    @Test
    @DisplayName("Test 2: When validating a null user, should throw illegal argument")
    void test_null_user_for_validUserRegistration() {
        //Act
        UserEntity validatedUser = null;
        //Arrange
        Assertions.assertNull(validatedUser);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUserRegistration(null);
        });
        assertEquals(exception.getMessage(), "User cannot be null");
    }

    @Test
    @DisplayName("Test 3: When validating a user with a null email address, should throw illegal argument")
    void test_null_emailaddress_for_validUserRegistration() {
        //Act
        testUser.setEmailAddress(null);
        //Arrange
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUserRegistration(testUser);
        });
        assertEquals("Email is required and cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test 4: When validating a user with a blank email address, should throw illegal argument")
    void test_blank_emailaddress_for_validUserRegistration() {
        //Act
        testUser.setEmailAddress("");
        //Arrange
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUserRegistration(testUser);
        });
        assertEquals("Email is required and cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test 5: When validating a user with a null password, should throw illegal argument")
    void test_null_password_for_validUserRegistration() {
        //Act
        testUser.setPassword(null);
        //Arrange
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUserRegistration(testUser);
        });
        assertEquals("Password is required and cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test 6: When validating a user with a null password, should throw illegal argument")
    void test_blank_password_for_validUserRegistration() {
        //Act
        testUser.setPassword("");
        //Arrange
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.validateUserRegistration(testUser);
        });
        assertEquals("Password is required and cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Test 7: When registering a new user, account should be locked and not enabled")
    void test_registerUser_returns_user_with_locked_and_disabled_account() {
        // Arrange
        // Mock the save operation to set an ID
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0);
            savedUser.setId(1L); //Simulates db assigning an ID
            return savedUser;
        });

        // Act
        UserEntity registeredUser = userService.registerUser(testUser);

        // Assert
        assertNotNull(registeredUser.getId());
        assertFalse(registeredUser.isAccountNonLocked()); // account is locked
        assertFalse(registeredUser.isEnabled());          // account is not enabled
    }

    @Test
    @DisplayName("Test 8: When registering a user that already has an existing email, throw new illegal argument exception")
    void test_registerUser_with_existing_email_throws_new_illegal_argument() {
        // Arrange
        String existingEmail = "example@email.com";

        //Mock the save operation for the first user
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            savedUser.setEmailAddress(existingEmail);
            return savedUser;
        });

        // Act - First registration
        UserEntity firstUser = new UserEntity();
        firstUser.setEmailAddress(existingEmail);
        firstUser.setPassword("password123");
        userService.registerUser(firstUser);

        // Mock that a user with this email already exists
        when(userRepository.findByEmailAddressIgnoreCase(existingEmail))
                .thenReturn(Optional.of(firstUser));  // return the existing user

        // Act & Assert - Second registration with same email should fail
        UserEntity secondUser = new UserEntity();
        secondUser.setEmailAddress(existingEmail);
        secondUser.setPassword("password123");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(secondUser)
        );
        assertEquals("Email is already registered", exception.getMessage());
    }

    // Note: loadUserByUsername tests have been removed as the method was moved to SecurityConfig
}
