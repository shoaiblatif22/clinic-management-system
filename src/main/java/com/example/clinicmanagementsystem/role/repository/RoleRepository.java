package com.example.clinicmanagementsystem.role.repository;

import com.example.clinicmanagementsystem.role.entity.RoleEntity;  // Importing RoleEntity
import com.example.clinicmanagementsystem.role.Role;  // Importing the enum Role
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findById(Integer roleId);
    Optional<RoleEntity> findByRoleName(Role roleName);
}
