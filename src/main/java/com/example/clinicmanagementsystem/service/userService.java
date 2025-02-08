package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    private UserRepository userRepository;

    public UserModel findAllByEmailAddress(String emailAddress){
        return userRepository.findByEmailAddress(emailAddress);
    }

    public List<UserModel> findAll(){
        return userRepository.findAll();
    }

}
