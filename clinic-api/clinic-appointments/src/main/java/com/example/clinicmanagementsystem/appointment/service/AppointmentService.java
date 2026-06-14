package com.example.clinicmanagementsystem.appointment.service;

import com.example.clinicmanagementsystem.appointment.dto.AppointmentResponse;
import com.example.clinicmanagementsystem.appointment.dto.CreateAppointmentRequest;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(CreateAppointmentRequest request, String userId);
    AppointmentResponse cancelAppointment(Long appointmentId, String userId);
    AppointmentResponse getAppointment(Long appointmentId, String userId);
    List<AppointmentResponse> getAppointments(String userId);
    void deleteAppointment(Long appointmentId, String userId);
}
