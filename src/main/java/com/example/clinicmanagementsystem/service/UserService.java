package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.model.UserModel;
import com.example.clinicmanagementsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    //Connects to UserRepo
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //ModelMapping between userModel(userDTO) and userEntity
    @Autowired
    private ModelMapper modelMapper;

    //Mappings
    public UserModel entityToModel(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserModel.class);
    }

    public UserEntity modelToEntity(UserEntity userModel) {
        return modelMapper.map(userModel, UserEntity.class);
    }

    //CRUD operations within service
    public Optional<UserEntity> getUserById(int id) {
        return userRepository.findById(id);
    }

    public UserModel saveUser(UserEntity userModel) {
        UserEntity userEntity = modelToEntity(userModel);
        userRepository.save(userEntity);
        return null;
    }

    public UserEntity findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public UserEntity update(int id, UserEntity user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (user.getFirstName() != null) {
                        existingUser.setFirstName(user.getFirstName());
                    }
                    if (user.getLastName() != null) {
                        existingUser.setLastName(user.getLastName());
                    }
                    if (user.getEmail() != null) {
                        existingUser.setEmail(user.getEmail());
                    }
                    if (user.getPassword() != null) {
                        existingUser.setPassword(user.getPassword());
                    } else {
                        return null;
                    }
                    return userRepository.save(existingUser);
                })
                .orElse(null); // Or throw an exception if appropriate
    }
}