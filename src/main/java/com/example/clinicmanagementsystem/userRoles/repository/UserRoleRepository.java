package com.example.clinicmanagementsystem.userRoles.repository;

import com.example.clinicmanagementsystem.userRoles.entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRolesEntity, Integer> {
    Optional<UserRolesEntity> findByUserId(Integer userId);
    Optional<UserRolesEntity> findByRoleId(Integer roleId);


}



