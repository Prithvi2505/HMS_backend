package com.example.HMS.Controller;

import com.example.HMS.DTO.MedicalRecordRequestDTO;
import com.example.HMS.DTO.MedicalRecordResponseDTO;
import com.example.HMS.Entity.MedicalRecord;
import com.example.HMS.Service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/create")
    public MedicalRecordResponseDTO createMedicalRecord(@RequestBody MedicalRecordRequestDTO dto) {
        return medicalRecordService.createMedicalRecord(dto);
    }

    @GetMapping
    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @GetMapping("/{id}")
    public MedicalRecordResponseDTO getMedicalRecordById(@PathVariable int id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMedicalRecord(@PathVariable int id) {
        medicalRecordService.deleteMedicalRecord(id);
    }
}

