package com.example.clinicmanagementsystem.appointment.service;

import com.example.clinicmanagementsystem.appointment.dto.AppointmentResponse;
import com.example.clinicmanagementsystem.appointment.dto.CreateAppointmentRequest;
import com.example.clinicmanagementsystem.appointment.entity.AppointmentEntity;
import com.example.clinicmanagementsystem.appointment.model.AppointmentStatus;
import com.example.clinicmanagementsystem.appointment.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request, String userId) {
        log.info("Creating appointment for user: {}", userId);

        AppointmentEntity appointment = AppointmentEntity.builder()
                .userId(userId)
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .status(AppointmentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        AppointmentEntity saved = appointmentRepository.save(appointment);
        log.info("Appointment created with id: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    @Transactional
    public AppointmentResponse cancelAppointment(Long appointmentId, String userId) {
        log.info("Cancelling appointment {} for user: {}", appointmentId, userId);

        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        if (!appointment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to cancel this appointment");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Appointment is already cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        AppointmentEntity saved = appointmentRepository.save(appointment);
        log.info("Appointment {} cancelled", appointmentId);
        return toResponse(saved);
    }

    @Override
    public AppointmentResponse getAppointment(Long appointmentId, String userId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        if (!appointment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to view this appointment");
        }
        return toResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAppointments(String userId) {
        return appointmentRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public void deleteAppointment(Long appointmentId, String userId) {
        log.info("Deleting appointment {} for user: {}", appointmentId, userId);
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        if (!appointment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to delete this appointment");
        }
        appointmentRepository.delete(appointment);
        log.info("Appointment {} deleted", appointmentId);
    }

    private AppointmentResponse toResponse(AppointmentEntity entity) {
        return AppointmentResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .appointmentDate(entity.getAppointmentDate())
                .appointmentTime(entity.getAppointmentTime())
                .reason(entity.getReason())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
