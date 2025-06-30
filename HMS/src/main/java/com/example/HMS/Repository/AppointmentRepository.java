package com.example.HMS.Repository;

import com.example.HMS.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByDoctorId(int doctorId);
    int countByDoctorIdAndDate(int doctorId, LocalDate date);
    List<Appointment> findByDoctorIdAndDateAndTime(int doctorId, LocalDate date, LocalTime time);

}
