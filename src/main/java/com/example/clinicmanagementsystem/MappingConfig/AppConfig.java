package com.example.clinicmanagementsystem.MappingConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig { // Or any suitable name

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
