package com.example.clinicmanagementsystem.repository;


import com.example.clinicmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
