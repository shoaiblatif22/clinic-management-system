package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.AppUser;
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
    public AppUser registerUser(AppUser appUser) {
        // Check if email already exists
        if (appUserRepository.findByEmailAddress(appUser.getEmailAddress()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }
        // Validate user fields
        if (appUser.getEmailAddress() == null || appUser.getEmailAddress().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (appUser.getPassword() == null || appUser.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        // Hash the password before saving
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        // Save and return user
        return appUserRepository.save(appUser);
    }
}
