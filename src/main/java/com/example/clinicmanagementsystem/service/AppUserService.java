package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.AppUser;
import com.example.clinicmanagementsystem.repository.AppUserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public AppUser registerUser(AppUser appUser, String password) {
        // Perform validation on the appUser object
        if (appUser == null || appUser.getEmailAddress() == null || appUser.getEmailAddress().isEmpty()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        // Hash the password
        String encodedPassword = passwordEncoder.encode(password);
        appUser.setPassword(encodedPassword);

        // Save the user to the database
        return appUserRepository.save(appUser);
    }
}