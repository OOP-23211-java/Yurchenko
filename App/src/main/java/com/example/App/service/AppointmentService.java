package com.example.App.service;

import com.example.App.model.Appointment;
import com.example.App.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;

    public void save(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepo.findByPatientId(patientId);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public boolean isPatientAlreadyBooked(Long patientId, LocalDateTime dateTime) {
        return appointmentRepo.existsByPatientIdAndDateTime(patientId, dateTime);
    }

    public void deleteAppointment(Long appointmentId) {
        appointmentRepo.deleteById(appointmentId);
    }

}
