package com.example.HMS.Controller;

import com.example.HMS.DTO.PatientRequestDTO;
import com.example.HMS.DTO.PatientResponseDTO;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/create")
    public PatientResponseDTO createPatient(@RequestBody PatientRequestDTO dto) {
        return patientService.createPatient(dto);
    }

    @GetMapping
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public PatientResponseDTO getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/update/{id}")
    public PatientResponseDTO updatePatient(@PathVariable int id, @RequestBody PatientRequestDTO dto) {
        return patientService.updatePatient(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
    }

    @PutMapping("/{patientId}/assign-doctor/{doctorId}")
    public PatientResponseDTO assignDoctor(@PathVariable int patientId, @PathVariable int doctorId) {
        return patientService.assignDoctor(patientId, doctorId);
    }

    @PutMapping("/{patientId}/assign-room/{roomId}")
    public PatientResponseDTO assignRoom(@PathVariable int patientId, @PathVariable int roomId) {
        return patientService.assignRoom(patientId, roomId);
    }
}

