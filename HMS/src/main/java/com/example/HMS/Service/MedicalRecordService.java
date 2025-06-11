package com.example.HMS.Service;

import com.example.HMS.Entity.MedicalRecord;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.MedicalRecordRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    public MedicalRecord saveMedicalRecord(MedicalRecord record, int patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        record.setPatient(patient);
        return medicalRecordRepository.save(record);
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecordById(int id) {
        return medicalRecordRepository.findById(id).orElse(null);
    }

    public void deleteMedicalRecord(int id) {
        medicalRecordRepository.deleteById(id);
    }
}

