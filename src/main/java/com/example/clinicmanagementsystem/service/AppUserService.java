package com.example.clinicmanagementsystem.service;

import com.example.clinicmanagementsystem.model.AppUserModel;
import com.example.clinicmanagementsystem.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    private boolean isPhoneNumberVerified(AppUserModel appUserModel) {
        String phoneNumber = appUserModel.getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        return isValidPhoneNumberFormat(phoneNumber);
    }

    private boolean isValidPhoneNumberFormat(String phoneNumber) {
        // Simple regex to validate UK phone numbers
        return phoneNumber.matches("^\\+44\\d{10}$");
    }
}
