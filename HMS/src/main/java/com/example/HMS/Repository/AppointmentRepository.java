package com.example.HMS.Repository;

import com.example.HMS.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByDoctorId(int doctorId);
    int countByDoctorIdAndDate(int doctorId, LocalDate date);
    List<Appointment> findByDoctorIdAndDateAndTime(int doctorId, LocalDate date, LocalTime time);
    List<Appointment> findByDoctorIdAndDate(int doctorId, LocalDate date);


    @Query("SELECT a.time FROM Appointment a WHERE a.doctor.id = :doctorId AND a.date = :date")
    List<LocalTime> findTimesByDoctorIdAndDate(@Param("doctorId") int doctorId, @Param("date") LocalDate date);


}
