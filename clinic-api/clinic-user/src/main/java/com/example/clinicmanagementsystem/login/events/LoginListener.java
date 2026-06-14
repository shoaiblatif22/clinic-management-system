package com.example.clinicmanagementsystem.login.events;

import com.example.clinicmanagementsystem.registration.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginListener {

    @EventListener
    public void handleLoginEvent(LoginEvent event) {
        UserEntity user = event.userEntity();
        log.info("User authenticated - email: {}, role: {}, verified: {}",
                user.getEmailAddress(),
                user.getUserRole(),
                user.isEnabled());
    }
}