package com.example.clinicmanagementsystem.registration.repository;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserEntity, Long> {
   Optional<UserEntity> findByEmailAddressIgnoreCase(String emailAddress);
}
