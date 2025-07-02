package com.example.App.service;

import com.example.App.model.Patient;
import com.example.App.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepo;

    public void savePatient(Patient patient) {
        patientRepo.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }


    public Optional<Patient> findById(Long id) {
        return patientRepo.findById(id);
    }

    public Patient getById(Long id) {
        return patientRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Пациент с id=" + id + " не найден"));
    }

    public Optional<Patient> findByEmailAndPassword(String email, String password) {
        return patientRepo.findAll().stream()
                .filter(p -> p.getEmail().equals(email) && p.getPassword().equals(password))
                .findFirst();
    }

    public Optional<Patient> findByEmail(String email) {
        return patientRepo.findAll().stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
    }


}
