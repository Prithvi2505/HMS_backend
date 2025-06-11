package com.example.HMS.Controller;

import com.example.HMS.DTO.DoctorRequestDTO;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody DoctorRequestDTO dto) {
        Doctor doctor = new Doctor(dto.getName(), dto.getEmail(), dto.getSpecialization(), dto.getYearOfExperience(), dto.getPassword());
        return ResponseEntity.ok(doctorService.saveDoctor(doctor));
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return doctor != null ? ResponseEntity.ok(doctor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
