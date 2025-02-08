package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmailAddress(String emailAddress);
}
