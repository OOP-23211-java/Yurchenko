package com.example.App.controller;

import com.example.App.model.Patient;
import com.example.App.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final PatientService patientService;

    @GetMapping("/")
    public String welcomePage() {
        return "welcome";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Patient patient, Model model) {
        if (!patient.getEmail().contains("@")) {
            model.addAttribute("error", "Некорректный email. Введите email с символом '@'.");
            model.addAttribute("patient", patient);
            return "register";
        }

        patientService.savePatient(patient);
        return "redirect:/doctors?patientId=" + patient.getId();
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        Optional<Patient> patientOpt = patientService.findByEmail(email);

        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            if (patient.getPassword().equals(password)) {
                return "redirect:/select-doctor?patientId=" + patient.getId();
            } else {
                model.addAttribute("error", "Неверный пароль.");
            }
        } else {
            model.addAttribute("error", "Пользователь с таким email не найден.");
        }

        return "login";
    }

}
