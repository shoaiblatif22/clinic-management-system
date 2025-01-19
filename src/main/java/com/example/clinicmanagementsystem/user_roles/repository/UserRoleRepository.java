package com.example.clinicmanagementsystem.user_roles.repository;

import com.example.clinicmanagementsystem.user_roles.entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRolesEntity, Integer> {
    Optional<UserRolesEntity> findByUserId(Integer userId);
    Optional<UserRolesEntity> findByRoleId(Integer roleId);
    Optional<UserRolesEntity> deleteByUserId(Integer userId);


}



