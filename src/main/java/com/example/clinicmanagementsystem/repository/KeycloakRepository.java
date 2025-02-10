package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeycloakRepository extends JpaRepository<UserEntity, Long> {
    @Override
    default <S extends UserEntity> S save(S entity) {
        return null;
    }
}
