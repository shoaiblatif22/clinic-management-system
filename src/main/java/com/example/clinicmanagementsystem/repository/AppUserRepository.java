package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailAddress(String emailAddress);
}
