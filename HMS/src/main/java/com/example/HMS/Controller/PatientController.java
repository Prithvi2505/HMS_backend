package com.example.HMS.Controller;

import com.example.HMS.DTO.PatientRequestDTO;
import com.example.HMS.DTO.PatientResponseDTO;
import com.example.HMS.DTO.PatientUpdateRequestDTO;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/create")
    public PatientResponseDTO createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        return patientService.createPatient(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public PatientResponseDTO getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public PatientResponseDTO updatePatient(@Valid @PathVariable int id, @RequestBody PatientUpdateRequestDTO dto) {
        return patientService.updatePatient(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public void deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
    }

    @PutMapping("/{patientId}/assign-doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public PatientResponseDTO assignDoctor(@PathVariable int patientId, @PathVariable int doctorId) {
        return patientService.assignDoctor(patientId, doctorId);
    }

    @PutMapping("/{patientId}/assign-room/{roomId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public PatientResponseDTO assignRoom(@PathVariable int patientId, @PathVariable int roomId) {
        return patientService.assignRoom(patientId, roomId);
    }
}

