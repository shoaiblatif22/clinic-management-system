package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //finds a list of Users
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
    //finds user by Id
    public UserEntity getUserById(int id){
        return userRepository.findById(id).get();
    }
    //saves a user
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }
    //deletes a user
    public void deleteUserById(int id){
        userRepository.deleteById(id);
    }
}
