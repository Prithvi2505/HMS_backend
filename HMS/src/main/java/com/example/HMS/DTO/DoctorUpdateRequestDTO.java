package com.example.HMS.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class DoctorUpdateRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @Min(value = 0, message = "Year Of Experience must be positive")
    private int yearOfExperience;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Min(value = 0, message = "Appointments Per Day must be positive")
    private int maxAppointmentsPerDay;

    @NotBlank(message = "Start Time is required")
    private String startTime;
    @NotBlank(message = "End Time is required")
    private String endTime;

    private List<String> availableDays;

    public DoctorUpdateRequestDTO(String name, String specialization, int yearOfExperience, String gender, int maxAppointmentsPerDay, String startTime, String endTime, List<String> availableDays) {
        this.name = name;
        this.specialization = specialization;
        this.yearOfExperience = yearOfExperience;
        this.gender = gender;
        this.maxAppointmentsPerDay = maxAppointmentsPerDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableDays = availableDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }
}
