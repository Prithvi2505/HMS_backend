package com.example.HMS.DTO;


import java.sql.Time;
import java.util.Date;

public class AppointmentResponseDTO {
    private int id;
    private Date date;
    private Time time;
    private int doctorId;
    private int patientId;

    public AppointmentResponseDTO() {
    }

    public AppointmentResponseDTO(int id, Date date, Time time, int doctorId, int patientId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
