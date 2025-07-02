package com.example.App;

import com.example.App.controller.AuthController;
import com.example.App.controller.MainController;
import com.example.App.model.Appointment;
import com.example.App.model.Doctor;
import com.example.App.model.Patient;
import com.example.App.service.AppointmentService;
import com.example.App.service.DoctorService;
import com.example.App.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerTests {

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @InjectMocks
    private MainController mainController;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient("Test User", "test@example.com", "pass");
        patient.setId(1L);
    }

    @Test
    void testWelcomePage() {
        assertEquals("welcome", authController.welcomePage());
    }

    @Test
    void testRegisterInvalidEmail() {
        patient.setEmail("invalidEmail");
        String view = authController.register(patient, model);
        assertEquals("register", view);
        verify(model).addAttribute(eq("error"), anyString());
    }

    @Test
    void testRegisterValid() {
        String view = authController.register(patient, model);
        verify(patientService).savePatient(patient);
        assertEquals("redirect:/doctors?patientId=" + patient.getId(), view);
    }

    @Test
    void testLoginSuccess() {
        when(patientService.findByEmail("test@example.com")).thenReturn(Optional.of(patient));
        String view = authController.login("test@example.com", "pass", model);
        assertEquals("redirect:/select-doctor?patientId=1", view);
    }

    @Test
    void testLoginWrongPassword() {
        when(patientService.findByEmail("test@example.com")).thenReturn(Optional.of(patient));
        String view = authController.login("test@example.com", "wrong", model);
        assertEquals("login", view);
        verify(model).addAttribute(eq("error"), anyString());
    }

    @Test
    void testLoginEmailNotFound() {
        when(patientService.findByEmail("unknown@example.com")).thenReturn(Optional.empty());
        String view = authController.login("unknown@example.com", "pass", model);
        assertEquals("login", view);
        verify(model).addAttribute(eq("error"), anyString());
    }

    @Test
    void testMakeAppointmentAlreadyBooked() {
        when(appointmentService.isPatientAlreadyBooked(eq(1L), any())).thenReturn(true);
        when(patientService.getById(1L)).thenReturn(patient);
        when(doctorService.getAllDoctors()).thenReturn(List.of());

        String view = mainController.makeAppointment(2L, 1L, "2025-06-10", "09:00", model);
        assertEquals("select-doctor", view);
        verify(model).addAttribute(eq("error"), anyString());
    }

    @Test
    void testCancelAppointment() {
        String view = mainController.cancelAppointment(10L, 1L);
        assertEquals("redirect:/appointments?patientId=1", view);
        verify(appointmentService).deleteAppointment(10L);
    }
}
