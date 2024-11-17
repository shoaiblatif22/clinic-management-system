package com.example.clinicmanagementsystem.user.service;

import com.example.clinicmanagementsystem.role.Role;
import com.example.clinicmanagementsystem.role.entity.RoleEntity;
import com.example.clinicmanagementsystem.role.repository.RoleRepository;
import com.example.clinicmanagementsystem.user.entity.UserEntity;
import com.example.clinicmanagementsystem.user.model.UserModel;
import com.example.clinicmanagementsystem.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //Connects to UserRepo
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
    //GET USER BY ID
    public Optional<UserModel> getUserById(int id) {
        return userRepository.findById(id)
                .map
                        (this::entityToModel);
    }

    //SAVE USER
    public UserModel saveUser(UserEntity userModel) {
        //FETCH ROLES
        RoleEntity adminRole = roleRepository.findByRoleName(Role.ADMIN)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        //SET ROLE FOR USER
        userModel.getRoles().add(adminRole);
        //SAVE USER ENTITY
        UserEntity userEntity = modelToEntity(userModel);
        userRepository.save(userEntity);
        //RETURN SAVED USER AS A USER_MODEL.
        return entityToModel(userEntity);
    }

    //FIND USER BY FIRST NAME
    public UserModel findByFirstName(String firstName) {
        UserEntity userEntity = userRepository.findByFirstName(firstName);
        return entityToModel(userEntity);
    }

    //FIND ALL USERS
    public List<UserModel> findAll() {
        return userRepository.findAll().stream().map(
                this::entityToModel
        ).toList();
    }

    //DELETE USER BY ID NUMBER
    public void deleteUserById(int id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //UPDATE USER BY ID NUMBER
    public UserModel update(int id, UserModel userModel) {
        return userRepository.findById(id)
                .map(existingUser ->
                {
                    //map usermodel to existing user
                    modelMapper.map(userModel, existingUser);

                    //UPDATE ROLE (IF NECESSARY)
                    existingUser.getRoles().clear();
                    RoleEntity adminRole = roleRepository.findByRoleName(Role.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Role not found"));
                    existingUser.getRoles().add(adminRole);

                    //explicity set user id
                    existingUser.setId(id);

                    //return entityToModel mapping
                    return entityToModel(userRepository.save(existingUser));
                })
        .orElse(null);
    }
}