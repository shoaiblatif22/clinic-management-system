package com.example.clinicmanagementsystem.user_roles.service;

import com.example.clinicmanagementsystem.user_roles.entity.UserRolesEntity;
import com.example.clinicmanagementsystem.user_roles.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public Optional<UserRolesEntity> getUserId(Integer userId) {
        return userRoleRepository.findById(userId);
    }

    public Optional<UserRolesEntity> getRoleId(Integer roleId) {
        return userRoleRepository.findById(roleId);
    }


}
