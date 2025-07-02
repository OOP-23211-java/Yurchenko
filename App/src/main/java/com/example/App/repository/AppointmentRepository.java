package com.example.App.repository;

import com.example.App.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    List<Appointment> findByPatientId(Long patientId);

    boolean existsByPatientIdAndDateTime(Long patientId, LocalDateTime dateTime);
}
