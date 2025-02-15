package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.repository.UserRepository;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    private final UserRepository userRepository;
    private final Keycloak keycloak;

    @Autowired
    public UserService(UserRepository userRepository, Keycloak keycloak) {
        this.userRepository = userRepository;
        this.keycloak = keycloak;
    }

    public void registerUser(UserModel userModel, String keycloakId) {
        if (userModel == null) {
            throw new IllegalArgumentException("UserModel cannot be null");
        }
        if (keycloakId == null || keycloakId.isEmpty()) {
            throw new IllegalArgumentException("Keycloak ID cannot be null or empty");
        }
        if (userRepository.findByKeycloakId(keycloakId).isPresent()) {
            throw new IllegalStateException("User with Keycloak ID " + keycloakId + " already exists");
        }
        if (!isEmailVerified(userModel)) {
            throw new IllegalArgumentException("Email verification failed");
        }
        if (!isPhoneNumberVerified(userModel)) {
            throw new IllegalArgumentException("Phone number verification failed");
        }
        UserEntity userEntity = new UserEntity(userModel, keycloakId);
        userRepository.save(userEntity);
    }
    private boolean isEmailVerified(UserModel userModel) {
        String emailAddress = userModel.getEmailAddress();
        if (emailAddress == null || emailAddress.isEmpty()) {
            return false;
        }
        return isEmailVerifiedInKeycloak(emailAddress);
    }

    private boolean isEmailVerifiedInKeycloak(String email) {
        List<UserRepresentation> users = keycloak.realm(serverUrl)
                .users()
                .search(email);

        if (!users.isEmpty()) {
            return users.get(0).isEmailVerified();
        }
        return false;
    }

    private boolean isPhoneNumberVerified(UserModel userModel) {
        String phoneNumber = userModel.getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return isValidPhoneNumberFormat(phoneNumber);
    }

    private boolean isValidPhoneNumberFormat(String phoneNumber) {
        // Simple regex to validate UK phone numbers
        return phoneNumber.matches("^\\+44\\d{10}$");
    }
}
