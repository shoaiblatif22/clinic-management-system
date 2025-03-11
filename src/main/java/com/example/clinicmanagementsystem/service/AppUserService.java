package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.entity.AppUser;
import com.example.clinicmanagementsystem.repository.AppUserRepository;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.BinaryEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        return appUserRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, emailAddress)));
    }

    public AppUser regiserUser(AppUser appUser, String password) {
        appUser.setPassword(passwordEncoder.encode(password));
        return appUserRepository.save(appUser);
    }
}
