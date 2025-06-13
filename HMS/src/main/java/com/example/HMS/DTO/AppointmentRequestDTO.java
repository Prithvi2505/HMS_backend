package com.example.HMS.DTO;

import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Date;

public class AppointmentRequestDTO {
    @NotNull(message = "Date is required")
    private Date date;

    @NotNull(message = "Time is required")
    private Time time;

    private int doctorId;
    private int patientId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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
