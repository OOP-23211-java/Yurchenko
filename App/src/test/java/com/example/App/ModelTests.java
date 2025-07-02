package com.example.App;

import com.example.App.model.Appointment;
import com.example.App.model.Doctor;
import com.example.App.model.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {

    @Test
    public void testDoctorModel() {
        Doctor doctor = new Doctor("Иван Иванов", "Терапевт");
        assertEquals("Иван Иванов", doctor.getName());
        assertEquals("Терапевт", doctor.getSpecialization());

        doctor.setName("Петр Петров");
        doctor.setSpecialization("Хирург");

        assertEquals("Петр Петров", doctor.getName());
        assertEquals("Хирург", doctor.getSpecialization());
    }

    @Test
    public void testPatientModel() {
        Patient patient = new Patient("Мария", "maria@example.com", "secret");
        assertEquals("Мария", patient.getName());
        assertEquals("maria@example.com", patient.getEmail());
        assertEquals("secret", patient.getPassword());

        patient.setName("Ольга");
        patient.setEmail("olga@example.com");
        patient.setPassword("newpass");

        assertEquals("Ольга", patient.getName());
        assertEquals("olga@example.com", patient.getEmail());
        assertEquals("newpass", patient.getPassword());
    }

    @Test
    public void testAppointmentModel() {
        Doctor doctor = new Doctor("Сергей", "Невролог");
        Patient patient = new Patient("Дмитрий", "dima@mail.com", "1234");
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 10, 10, 0);

        Appointment appointment = new Appointment(doctor, patient, dateTime);

        assertEquals(doctor, appointment.getDoctor());
        assertEquals(patient, appointment.getPatient());
        assertEquals(dateTime, appointment.getDateTime());

        // изменения
        LocalDateTime newTime = LocalDateTime.of(2025, 6, 11, 12, 0);
        appointment.setDateTime(newTime);
        assertEquals(newTime, appointment.getDateTime());
    }
}
