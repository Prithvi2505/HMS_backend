package com.example.HMS.DTO;

import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Date;

public class AppointmentRequestDTO {
    @NotNull(message = "Date is required")
    private String date;

    @NotNull(message = "Time is required")
    private String time;

    private int doctorId;
    private int patientId;

    public AppointmentRequestDTO(String date, String time, int doctorId, int patientId) {
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
