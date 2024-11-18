package com.example.clinicmanagementsystem.role.service;

import com.example.clinicmanagementsystem.role.entity.RoleEntity;
import com.example.clinicmanagementsystem.role.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<RoleEntity> getRoleById(Integer roleId) {
        return roleRepository.findById(roleId);
    }
}
