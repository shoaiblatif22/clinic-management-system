package com.example.clinicmanagementsystem.role.repository;

import com.example.clinicmanagementsystem.role.Role;
import com.example.clinicmanagementsystem.role.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<RoleEntity> findByRoleName(Role roleName);
}
