package com.example.App;

import com.example.App.model.Doctor;
import com.example.App.service.DoctorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner initDoctors(DoctorService doctorService) {
		return args -> {
			if (doctorService.getAllDoctors().isEmpty()) {
				doctorService.saveDoctor(new Doctor("Иванов Иван Иванович", "Терапевт"));
				doctorService.saveDoctor(new Doctor("Смирнова Мария Сергеевна", "Кардиолог"));
				doctorService.saveDoctor(new Doctor("Ким Алексей Петрович", "Хирург"));
			}
		};
	}
}
