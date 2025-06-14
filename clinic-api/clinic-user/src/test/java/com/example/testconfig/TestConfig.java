package com.example.testconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({
    "com.example.clinicmanagementsystem",
    "com.example.clinicmanagementsystem.registration",
    "com.example.clinicmanagementsystem.password_reset",
    "com.example.clinicmanagementsystem.login"
})
@EnableJpaRepositories({
    "com.example.clinicmanagementsystem.registration.repository",
    "com.example.clinicmanagementsystem.password_reset.repository"
})
@EntityScan({
    "com.example.clinicmanagementsystem.registration.entity",
    "com.example.clinicmanagementsystem.password_reset.entity"
})
public class TestConfig {
    public static void main(String[] args) {
        SpringApplication.run(TestConfig.class, args);
    }
}
