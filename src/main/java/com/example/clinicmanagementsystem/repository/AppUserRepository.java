package com.example.clinicmanagementsystem.repository;

import com.example.clinicmanagementsystem.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.model.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<ClinicAppUser, Long> {
   Optional<ClinicAppUser> findByEmailAddress(String emailAddress);
   Optional<ClinicAppUser> findByFirstNameAndLastName(String firstName, String lastName);
   List<ClinicAppUser> findByAppUserRole(AppUserRole role);
   List<ClinicAppUser> findByEnabled(boolean enabled);
}
