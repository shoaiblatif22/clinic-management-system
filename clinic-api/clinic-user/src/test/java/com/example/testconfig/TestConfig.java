package com.example.testconfig;

import jakarta.mail.internet.MimeMessage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Configuration
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = {
    "com.example.clinicmanagementsystem.password_reset.controller",
    "com.example.clinicmanagementsystem.password_reset.service"
})
@EnableJpaRepositories(basePackages = {
    "com.example.clinicmanagementsystem.registration.repository",
    "com.example.clinicmanagementsystem.password_reset.repository"
})
@EntityScan(basePackages = {
    "com.example.clinicmanagementsystem.registration.entity",
    "com.example.clinicmanagementsystem.password_reset.entity"
})
@TestPropertySource("classpath:application-test.properties")
public class TestConfig {
    
    @Bean
    @Primary
    public JavaMailSender javaMailSender() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        doNothing().when(mailSender).send(any(MimeMessage.class));
        return mailSender;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Primary
    public JavaMailSender mailSender() {
        return mock(JavaMailSender.class);
    }
}
