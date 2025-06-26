package com.example.HMS.Controller;

import com.example.HMS.DTO.MedicalRecordRequestDTO;
import com.example.HMS.DTO.MedicalRecordResponseDTO;
import com.example.HMS.Entity.MedicalRecord;
import com.example.HMS.Service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medical-records")
@CrossOrigin
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Operation(summary = "Creating Medical Record")
    @PostMapping()
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public MedicalRecordResponseDTO createMedicalRecord(@Valid @RequestBody MedicalRecordRequestDTO dto) {
        return medicalRecordService.createMedicalRecord(dto);
    }

    @Operation(summary = "Getting All Medical Records")
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT','STAFF')")
    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @Operation(summary = "Getting Medical Record by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT','STAFF')")
    public MedicalRecordResponseDTO getMedicalRecordById(@PathVariable int id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @Operation(summary = "Deleting Medical Record by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public void deleteMedicalRecord(@PathVariable int id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

    @Operation(summary = "Getting Medical Record Based by Patient ID")
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT','STAFF')")
    public List<MedicalRecordResponseDTO> getRecordsByPatient(@PathVariable int patientId) {
        return medicalRecordService.getRecordsByPatientId(patientId);
    }

    @Operation(summary = "Updating Medical Record by ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public MedicalRecordResponseDTO updateMedicalRecord(@PathVariable int id, @RequestBody MedicalRecordRequestDTO dto) {
        return medicalRecordService.updateMedicalRecord(id, dto);
    }

}

