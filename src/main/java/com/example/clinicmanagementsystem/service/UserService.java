package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }
//
//    public void deleteUserById(Long id) {
//        userRepository.deleteById(id);
//    }
}