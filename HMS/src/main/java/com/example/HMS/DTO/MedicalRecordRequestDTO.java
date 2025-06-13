package com.example.HMS.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class MedicalRecordRequestDTO {
    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @Min(value = 1900, message = "Year must be valid")
    private int yearOfDiagnosis;

    @NotBlank(message = "Medicine used is required")
    private String medicineUsed;

    public MedicalRecordRequestDTO(String diagnosis, int yearOfDiagnosis, String medicineUsed, int patientId) {
        this.diagnosis = diagnosis;
        this.yearOfDiagnosis = yearOfDiagnosis;
        this.medicineUsed = medicineUsed;
        this.patientId = patientId;
    }

    private int patientId;

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getYearOfDiagnosis() {
        return yearOfDiagnosis;
    }

    public void setYearOfDiagnosis(int yearOfDiagnosis) {
        this.yearOfDiagnosis = yearOfDiagnosis;
    }

    public String getMedicineUsed() {
        return medicineUsed;
    }

    public void setMedicineUsed(String medicineUsed) {
        this.medicineUsed = medicineUsed;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
