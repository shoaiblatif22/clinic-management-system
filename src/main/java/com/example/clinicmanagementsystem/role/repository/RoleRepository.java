package com.example.clinicmanagementsystem.role.repository;

import com.example.clinicmanagementsystem.role.entity.RoleEntity;  // Importing RoleEntity
import com.example.clinicmanagementsystem.role.Role;  // Importing the enum Role
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(Role roleName);  // RoleEntity has roleName of type Role enum
}
