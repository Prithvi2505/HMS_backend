package com.example.HMS.Controller;

import com.example.HMS.DTO.PatientRequestDTO;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody PatientRequestDTO dto) {
        Patient patient = new Patient(dto.getName(), dto.getEmail(), dto.getAge(), dto.getGender(), dto.getMobileNo(), dto.getCity(), dto.getPassword());
        return ResponseEntity.ok(patientService.savePatient(patient));
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        Patient patient = patientService.getPatientById(id);
        return patient != null ? ResponseEntity.ok(patient) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/assign-doctor")
    public ResponseEntity<Patient> assignDoctor(@PathVariable int id, @RequestParam int doctorId) {
        return ResponseEntity.ok(patientService.assignDoctorToPatient(id, doctorId));
    }

    @PostMapping("/{id}/assign-room")
    public ResponseEntity<Patient> assignRoom(@PathVariable int id, @RequestParam int roomId) {
        return ResponseEntity.ok(patientService.assignRoomToPatient(id, roomId));
    }
}

