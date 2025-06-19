package com.example.HMS.Service;

import com.example.HMS.DTO.MedicalRecordRequestDTO;
import com.example.HMS.DTO.MedicalRecordResponseDTO;
import com.example.HMS.Entity.MedicalRecord;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.MedicalRecordRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository recordRepository;
    @Autowired
    private PatientRepository patientRepository;

    public MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO dto) {
        MedicalRecord record = new MedicalRecord();
        record.setDiagnosis(dto.getDiagnosis());
        record.setYearOfDiagnosis(dto.getYearOfDiagnosis());
        record.setMedicineUsed(dto.getMedicineUsed());
        record.setPatient(patientRepository.findById(dto.getPatientId()).orElseThrow());
        return toResponseDTO(recordRepository.save(record));
    }

    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {
        return recordRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public MedicalRecordResponseDTO getMedicalRecordById(int id) {
        return toResponseDTO(recordRepository.findById(id).orElseThrow());
    }

    public void deleteMedicalRecord(int id) {
        recordRepository.deleteById(id);
    }

    public List<MedicalRecordResponseDTO> getRecordsByPatientId(int patientId) {
        return recordRepository.findByPatientId(patientId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public MedicalRecordResponseDTO updateMedicalRecord(int id, MedicalRecordRequestDTO dto) {
        MedicalRecord existingRecord = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with ID: " + id));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));
        existingRecord.setDiagnosis(dto.getDiagnosis());
        existingRecord.setYearOfDiagnosis(dto.getYearOfDiagnosis());
        existingRecord.setMedicineUsed(dto.getMedicineUsed());
        existingRecord.setPatient(patient);
        MedicalRecord updated = recordRepository.save(existingRecord);
        return toResponseDTO(updated);
    }


    private MedicalRecordResponseDTO toResponseDTO(MedicalRecord record) {
        MedicalRecordResponseDTO dto = new MedicalRecordResponseDTO();
        dto.setId(record.getId());
        dto.setDiagnosis(record.getDiagnosis());
        dto.setYearOfDiagnosis(record.getYearOfDiagnosis());
        dto.setMedicineUsed(record.getMedicineUsed());
        dto.setPatientId(record.getPatient().getId());
        return dto;
    }

}

