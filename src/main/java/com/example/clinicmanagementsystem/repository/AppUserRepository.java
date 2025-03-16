package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.entity.AppUser;
import com.example.clinicmanagementsystem.model.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
   Optional<AppUser> findByEmailAddress(String emailAddress);
   Optional<AppUser> findByFirstNameAndLastName(String firstName, String lastName);
   List<AppUser> findByAppUserRole(AppUserRole role);
   List<AppUser> findByEnabled(boolean enabled);
}
