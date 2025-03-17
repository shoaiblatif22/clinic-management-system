package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.ClinicAppUser;
import com.example.clinicmanagementsystem.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public ClinicAppUser registerUser(ClinicAppUser clinicAppUser) {
        // Check if email already exists
        if (appUserRepository.findByEmailAddress(clinicAppUser.getEmailAddress()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }
        // Validate user fields
        if (clinicAppUser.getEmailAddress() == null || clinicAppUser.getEmailAddress().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (clinicAppUser.getPassword() == null || clinicAppUser.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        // Hash the password before saving
        clinicAppUser.setPassword(passwordEncoder.encode(clinicAppUser.getPassword()));
        // Save and return user
        return appUserRepository.save(clinicAppUser);
    }
}
