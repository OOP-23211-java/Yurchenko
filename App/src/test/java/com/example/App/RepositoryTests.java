package com.example.App;

import com.example.App.model.Appointment;
import com.example.App.model.Doctor;
import com.example.App.model.Patient;
import com.example.App.repository.AppointmentRepository;
import com.example.App.repository.DoctorRepository;
import com.example.App.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class RepositoryTests {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @BeforeEach
    void clearDatabase() {
        appointmentRepo.deleteAll();
        patientRepo.deleteAll();
        doctorRepo.deleteAll();
    }

    @Test
    public void testDoctorRepository() {
        Doctor doctor = new Doctor("Александр", "Хирург");
        doctorRepo.save(doctor);

        List<Doctor> allDoctors = doctorRepo.findAll();
        assertThat(allDoctors).hasSize(1);
        assertThat(allDoctors.get(0).getName()).isEqualTo("Александр");
    }

    @Test
    public void testPatientRepository() {
        Patient patient = new Patient("Елена", "elena@mail.com", "pass123");
        patientRepo.save(patient);

        List<Patient> allPatients = patientRepo.findAll();
        assertThat(allPatients).hasSize(1);
        assertThat(allPatients.get(0).getEmail()).isEqualTo("elena@mail.com");
    }

    @Test
    public void testAppointmentRepository() {
        Doctor doctor = doctorRepo.save(new Doctor("Иван", "Терапевт"));
        Patient patient = patientRepo.save(new Patient("Анна", "anna@example.com", "1234"));
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 18, 9, 0);

        Appointment appointment = new Appointment(doctor, patient, dateTime);
        appointmentRepo.save(appointment);

        List<Appointment> found = appointmentRepo.findByPatientId(patient.getId());
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getDoctor().getName()).isEqualTo("Иван");

        boolean exists = appointmentRepo.existsByPatientIdAndDateTime(patient.getId(), dateTime);
        assertThat(exists).isTrue();
    }
}
