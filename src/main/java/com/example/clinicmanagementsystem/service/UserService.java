package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserEntity findAllByEmailAddress(String emailAddress){
        return userRepository.findByEmailAddress(emailAddress);
    }
    public List<UserEntity> findAll(){
        return userRepository.findAll();
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
        //We need to call the verification methods here before we register our user in db

        UserEntity userEntity = new UserEntity(userModel, keycloakId);
        userRepository.save(userEntity);
    }

    private boolean isEmailVerified(UserModel userModel) {
        String emailAddress = userModel.getEmailAddress();

        return emailAddress != null && !emailAddress.isEmpty();
    };
    private boolean isPhoneNumberVerified(UserModel userModel) {
        Integer phoneNumber = userModel.getPhoneNumber();

        return phoneNumber != null && phoneNumber > 0;
    };
}
