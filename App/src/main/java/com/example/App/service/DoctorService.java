package com.example.App.service;

import com.example.App.model.Doctor;
import com.example.App.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepo;

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getById(Long id) {
        return doctorRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Doctor not found with id = " + id));
    }

    public void saveDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
    }

    public boolean isEmpty() {
        return doctorRepo.count() == 0;
    }
}
