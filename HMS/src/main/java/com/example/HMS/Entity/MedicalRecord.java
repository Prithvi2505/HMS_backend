package com.example.HMS.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "year_of_diagnosis")
    private int yearOfDiagnosis;

    @Column(name="medicine_used")
    private String medicineUsed;

    @ManyToOne
    @JoinColumn(name = "patientId",nullable = false)
    @JsonIgnore
    private Patient patient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicineUsed() {
        return medicineUsed;
    }

    public void setMedicineUsed(String medicineUsed) {
        this.medicineUsed = medicineUsed;
    }

    public int getYearOfDiagnosis() {
        return yearOfDiagnosis;
    }

    public void setYearOfDiagnosis(int yearOfDiagnosis) {
        this.yearOfDiagnosis = yearOfDiagnosis;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
