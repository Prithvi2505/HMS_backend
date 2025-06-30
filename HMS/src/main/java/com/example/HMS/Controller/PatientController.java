package com.example.HMS.Controller;

import com.example.HMS.DTO.PatientNameIdDTO;
import com.example.HMS.DTO.PatientRequestDTO;
import com.example.HMS.DTO.PatientResponseDTO;
import com.example.HMS.DTO.PatientUpdateRequestDTO;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
@CrossOrigin
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Operation(summary = "Creating Patient")
    @PostMapping()
    public PatientResponseDTO createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        return patientService.createPatient(dto);
    }

    @Operation(summary = "Getting List of All Patient")
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @Operation(summary = "Getting Patient by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public PatientResponseDTO getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id);
    }

    @Operation(summary = "Updating Patient by ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public PatientResponseDTO updatePatient(@Valid @PathVariable int id, @RequestBody PatientUpdateRequestDTO dto) {
        return patientService.updatePatient(id, dto);
    }

    @Operation(summary = "Getting Patient by Name")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<PatientNameIdDTO> searchPatients(@RequestParam String name) {
        return patientService.searchByName(name);
    }

    @Operation(summary = "Deleting Patient by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public void deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
    }


    @Operation(summary = "Assigning Patient to Doctor by Their respective ID")
    @PutMapping("/{patientId}/assign-doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public PatientResponseDTO assignDoctor(@PathVariable int patientId, @PathVariable int doctorId) {
        return patientService.assignDoctor(patientId, doctorId);
    }

    @Operation(summary = "Assigning Patient to Room by Their respective ID")
    @PutMapping("/{patientId}/assign-room/{roomId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public PatientResponseDTO assignRoom(@PathVariable int patientId, @PathVariable int roomId) {
        return patientService.assignRoom(patientId, roomId);
    }
}

