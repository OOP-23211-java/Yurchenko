package com.example.App;

import com.example.App.model.Appointment;
import com.example.App.model.Doctor;
import com.example.App.model.Patient;
import com.example.App.repository.AppointmentRepository;
import com.example.App.repository.DoctorRepository;
import com.example.App.repository.PatientRepository;
import com.example.App.service.AppointmentService;
import com.example.App.service.DoctorService;
import com.example.App.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@DataJpaTest
@Import({AppointmentService.class, DoctorService.class, PatientService.class})
public class ServiceTests {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setUp() {
        doctor = new Doctor("Доктор Тест", "Терапевт");
        doctorService.saveDoctor(doctor);

        patient = new Patient("Пациент Тест", "test@example.com", "1234");
        patientService.savePatient(patient);
    }

    @Test
    void testSaveAndFindAppointment() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment appointment = new Appointment(doctor, patient, dateTime);
        appointmentService.save(appointment);

        assertTrue(appointmentService.getAppointmentsByPatientId(patient.getId()).stream()
                .anyMatch(a -> a.getDateTime().equals(dateTime)));
    }

    @Test
    void testIsPatientAlreadyBooked() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        appointmentService.save(new Appointment(doctor, patient, dateTime));

        assertTrue(appointmentService.isPatientAlreadyBooked(patient.getId(), dateTime));
    }

    @Test
    void testDeleteAppointment() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment appointment = new Appointment(doctor, patient, dateTime);
        appointmentService.save(appointment);

        Long id = appointment.getId();
        appointmentService.deleteAppointment(id);

        assertFalse(appointmentRepo.findById(id).isPresent());
    }

    @Test
    void testDoctorServiceGetById() {
        Doctor found = doctorService.getById(doctor.getId());
        assertEquals(doctor.getName(), found.getName());
    }

    @Test
    void testPatientServiceGetByIdAndEmail() {
        Patient foundById = patientService.getById(patient.getId());
        assertEquals(patient.getEmail(), foundById.getEmail());

        Optional<Patient> foundByEmail = patientService.findByEmail(patient.getEmail());
        assertTrue(foundByEmail.isPresent());
        assertEquals(patient.getName(), foundByEmail.get().getName());
    }

    @Test
    void testPatientServiceFindByEmailAndPassword() {
        Optional<Patient> found = patientService.findByEmailAndPassword(patient.getEmail(), patient.getPassword());
        assertTrue(found.isPresent());
        assertEquals(patient.getName(), found.get().getName());
    }
}
