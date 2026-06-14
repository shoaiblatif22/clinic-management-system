package com.example.clinicmanagementsystem.appointment.repository;

import com.example.clinicmanagementsystem.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByUserId(String userId);
}
