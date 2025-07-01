package com.example.HMS.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "year_of_Experience")
    private int yearOfExperience;

    @Column(name = "password")
    private String password;

    @Column(name = "maxAppointmentsPerDay")
    private int maxAppointmentsPerDay;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "available_days")
    private List<DayOfWeek> availableDays;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor() {}

    public Doctor(int id, String name, String email, String gender, String specialization, int yearOfExperience, String password, int maxAppointmentsPerDay, LocalTime startTime, LocalTime endTime, List<DayOfWeek> availableDays, List<Patient> patients, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.specialization = specialization;
        this.yearOfExperience = yearOfExperience;
        this.password = password;
        this.maxAppointmentsPerDay = maxAppointmentsPerDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableDays = availableDays;
        this.patients = patients;
        this.appointments = appointments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(int yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getMaxAppointmentsPerDay() {
        return maxAppointmentsPerDay;
    }

    public void setMaxAppointmentsPerDay(int maxAppointmentsPerDay) {
        this.maxAppointmentsPerDay = maxAppointmentsPerDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<DayOfWeek> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<DayOfWeek> availableDays) {
        this.availableDays = availableDays;
    }
}
