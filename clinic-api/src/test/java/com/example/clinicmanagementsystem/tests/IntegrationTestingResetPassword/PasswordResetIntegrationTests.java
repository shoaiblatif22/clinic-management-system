package com.example.clinicmanagementsystem.tests.IntegrationTestingResetPassword;

import com.example.clinicmanagementsystem.ClinicManagementSystemApplication;
import com.example.clinicmanagementsystem.user.password_reset.entity.PasswordResetToken;
import com.example.clinicmanagementsystem.user.password_reset.repository.PasswordResetTokenRepository;
import com.example.clinicmanagementsystem.user.registration.entity.UserEntity;
import com.example.clinicmanagementsystem.user.registration.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Date;

import static com.example.clinicmanagementsystem.user.registration.model.UserRole.USER;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ClinicManagementSystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PasswordResetIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser;

    String VALID_TOKEN = "validToken";
    String INVALID_TOKEN = "invalidToken";

    @BeforeEach
    void setUp() {
        // Clear repositories first
        tokenRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new UserEntity(
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "MALE",
                "1234567890",
                "test@example.com",
                "123 Test St",
                "Apt 4B",
                "Test City",
                "12345",
                "Test County",
                "Test Country",
                "password123",
                USER,
                false,
                true
        );
        testUser = userRepository.save(testUser);
    }

    @Test
    @DisplayName("Request password reset with valid email which should return created and token is saved")
    public void requestPasswordReset_WithValidEmail_ShouldReturnCreated_AndTokenIsSaved() throws Exception {
        // Arrange
        String requestBody = "{\"emailAddress\":\"test@example.com\"}";

        // Act & Assert
        mockMvc.perform(post("/user/api/v1/password-reset/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful());

        // Verify token was saved
        assertTrue(tokenRepository.findByUser(testUser).isPresent(),
                "Token should be saved in repository");
    }

    @Test
    @DisplayName("Request password reset with non-existent email which should return bad request")
    public void requestPasswordReset_WithNonExistentEmail_ShouldReturnBadRequest() throws Exception {
        String requestBody = "{\"emailAddress\":\"nonexistent@example.com\"}";

        mockMvc.perform(post("/user/api/v1/password-reset/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("User not found")));
    }

    @Test
    @DisplayName("Request password reset with empty email which should return bad request")
    public void requestPasswordReset_WithEmptyEmail_ShouldReturnBadRequest() throws Exception {
        String requestBody = "{\"emailAddress\":\"\"}";

        mockMvc.perform(post("/user/api/v1/password-reset/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.emailAddress").exists());
    }

    @Test
    @DisplayName("Reset password with expired token should return bad request")
    public void resetPassword_WithExpiredToken_ShouldReturnBadRequest() throws Exception {
        // Create an expired token
        PasswordResetToken token = new PasswordResetToken(INVALID_TOKEN, testUser);
        
        // Manually set expiry date to the past
        token.setExpiryDate(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)); // 24 hours ago
        tokenRepository.save(token);

        String newPassword = "NewPassword123#";
        String requestBody = String.format("token=%s&newPassword=%s", INVALID_TOKEN, newPassword);

        // Act & Assert
        mockMvc.perform(post("/user/api/v1/password-reset/reset")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(requestBody))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Token has expired")));
    }

    @Test
    @Disabled
    public void resetPassword_WithWeakPassword_ShouldReturnBadRequest() throws Exception {
        //Arrange request body
        String newPassword = "NewPassword123!"; //Weak password as it contains exclamation mark
        String requestBody = String.format("token=%s&newPassword=%s", VALID_TOKEN, newPassword);

        //Act & Assert
        mockMvc.perform(post("/user/api/v1/password-reset/reset")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(requestBody))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("" +
                        "Password must be at least 8 characters long, " +
                        "contain at least one digit, " +
                        "one lowercase letter, " +
                        "one uppercase letter, " +
                        "one special character (@#$%^&+=), " +
                        "and no whitespace")));
    }

    @Test
    @Disabled
    public void consecutivePasswordResetRequests_ShouldInvalidatePreviousTokens() throws Exception {
    }

    @Test
    @Disabled
    public void passwordReset_ForDisabledAccount_ShouldReturnBadRequest() throws Exception {
    }

    @Test
    @Disabled
    public void passwordReset_EmailCaseInsensitive_ShouldWork() throws Exception {
    }

}