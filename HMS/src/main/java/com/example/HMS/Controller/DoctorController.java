package com.example.HMS.Controller;

import com.example.HMS.DTO.DoctorRequestDTO;
import com.example.HMS.DTO.DoctorResponseDTO;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/create")
    public DoctorResponseDTO createDoctor(@RequestBody DoctorRequestDTO dto) {
        return doctorService.createDoctor(dto);
    }

    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable int id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/update/{id}")
    public DoctorResponseDTO updateDoctor(@PathVariable int id, @RequestBody DoctorRequestDTO dto) {
        return doctorService.updateDoctor(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
    }
}
