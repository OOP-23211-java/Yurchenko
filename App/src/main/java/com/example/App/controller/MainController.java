package com.example.App.controller;

import com.example.App.model.Appointment;
import com.example.App.service.AppointmentService;
import com.example.App.service.DoctorService;
import com.example.App.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java. time. LocalDate;



import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointment")
    public String makeAppointment(@RequestParam Long doctorId,
                                  @RequestParam Long patientId,
                                  @RequestParam String date,
                                  @RequestParam String time,
                                  Model model) {
        if (date == null || time == null || date.isBlank() || time.isBlank()) {
            model.addAttribute("error", "Выберите дату и время");
        } else {
            String dateTimeStr = date + "T" + time;
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);

            if (appointmentService.isPatientAlreadyBooked(patientId, dateTime)) {
                model.addAttribute("error", "У вас уже есть запись на это время.");
            } else {
                Appointment a = new Appointment();
                a.setDoctor(doctorService.getById(doctorId));
                a.setPatient(patientService.getById(patientId));
                a.setDateTime(dateTime);
                appointmentService.save(a);

                return "redirect:/appointments?patientId=" + patientId;
            }
        }

        model.addAttribute("patientId", patientId);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patient", patientService.getById(patientId));
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedTime", time);
        model.addAttribute("selectedDoctorId", doctorId);

        return "select-doctor";
    }




    @GetMapping("/appointments")
    public String viewAppointments(@RequestParam Long patientId, Model model) {
        model.addAttribute("appointments", appointmentService.getAppointmentsByPatientId(patientId));
        model.addAttribute("patientId", patientId);
        model.addAttribute("patient", patientService.getById(patientId));
        return "appointments";
    }

    @GetMapping("/select-doctor")
    public String selectDoctor(@RequestParam Long patientId, Model model) {
        model.addAttribute("patientId", patientId);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patient", patientService.getById(patientId));

        List<String> dates = IntStream.range(0, 7)
                .mapToObj(i -> LocalDate.now().plusDays(i).toString())
                .collect(Collectors.toList());
        model.addAttribute("dates", dates);

        List<String> times = List.of("09:00", "10:00", "11:00", "12:00", "13:00", "14:00");
        model.addAttribute("times", times);

        return "select-doctor";
    }


    @GetMapping("/doctors")
    public String listDoctors(@RequestParam Long patientId, Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patientId", patientId);
        return "doctors";
    }

    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @PostMapping("/cancel-appointment")
    public String cancelAppointment(@RequestParam Long appointmentId,
                                    @RequestParam Long patientId) {
        appointmentService.deleteAppointment(appointmentId);
        return "redirect:/appointments?patientId=" + patientId;
    }
}
