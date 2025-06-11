package com.example.HMS.DTO;

public class MedicalRecordRequestDTO {
    private String diagnosis;
    private int yearOfDiagnosis;
    private String medicineUsed;
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
