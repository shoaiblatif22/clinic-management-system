package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.entity.UserEntity;
import com.example.clinicmanagementsystem.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailAddress(String emailAddress);
    Optional<Object> findByKeycloakId(String keycloakId);
}
