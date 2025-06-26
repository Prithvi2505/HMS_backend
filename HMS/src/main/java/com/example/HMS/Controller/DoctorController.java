package com.example.HMS.Controller;

import com.example.HMS.DTO.DoctorRequestDTO;
import com.example.HMS.DTO.DoctorResponseDTO;
import com.example.HMS.DTO.DoctorUpdateRequestDTO;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation(summary = "Creating Doctor")
    @PostMapping()
    public DoctorResponseDTO createDoctor(@Valid @RequestBody DoctorRequestDTO dto) {
        return doctorService.createDoctor(dto);
    }

    @Operation(summary = "Getting the List of Doctor")
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @Operation(summary = "Getting Doctor by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public DoctorResponseDTO getDoctorById(@PathVariable int id) {
        return doctorService.getDoctorById(id);
    }

    @Operation(summary = "Updating Doctor by ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public DoctorResponseDTO updateDoctor(@Valid @PathVariable int id, @RequestBody DoctorUpdateRequestDTO dto) {
        return doctorService.updateDoctor(id, dto);
    }

    @Operation(summary = "Deleting Doctor by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public void deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
    }
}
