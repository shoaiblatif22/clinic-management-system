package com.example.clinicmanagementsystem.repository;


import com.example.clinicmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    UserEntity findById(long id);
    UserEntity save(UserEntity user);
    UserEntity findByFirstName(String firstName);
//    UserEntity update(UserEntity user);
//    void deleteById(int id);

}
