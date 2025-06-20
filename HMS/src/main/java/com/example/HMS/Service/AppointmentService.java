package com.example.HMS.Service;

import com.example.HMS.DTO.AppointmentRequestDTO;
import com.example.HMS.DTO.AppointmentResponseDTO;
import com.example.HMS.Entity.Appointment;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.AppointmentRepository;
import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date utilDate = formatter.parse(dto.getDate());
//            Date sqlDate = new Date(utilDate.getTime());
//            Time parsedTime = Time.valueOf(dto.getTime() + ":00");
//            LocalDate localDate = LocalDate.parse(dto.getDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dto.getDate(), formatter);
//            Date sqlDate = Date.valueOf(localDate);
            LocalTime localTime = LocalTime.parse(dto.getTime());
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            Appointment appointment = new Appointment();
            appointment.setDate(localDate);
            appointment.setTime(localTime);
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            Appointment saved = appointmentRepository.save(appointment);
            return new AppointmentResponseDTO(saved.getId(), saved.getDate(), saved.getTime(), doctor.getId(), patient.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error creating appointment: " + e.getMessage(), e);
        }
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public AppointmentResponseDTO getAppointmentById(int id) {
        return toResponseDTO(appointmentRepository.findById(id).orElseThrow());
    }

    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }

    public List<AppointmentResponseDTO> getAppointmentsByPatientId(int patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAppointmentsByDoctorId(int doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
    public AppointmentResponseDTO updateAppointment(int id, AppointmentRequestDTO dto) {
        int loggedInUserId = authUserDetailsService.getLoggedInUserId();
        String role = authUserDetailsService.getLoggedInUserRole();

        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

        if (role.equals("PATIENT") && existingAppointment.getPatient().getId() != loggedInUserId) {
            throw new AccessDeniedException("You can only update your own appointment");
        }

        if (role.equals("DOCTOR") && existingAppointment.getDoctor().getId() != loggedInUserId) {
            throw new AccessDeniedException("You can only update appointments assigned to you");
        }
        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date utilDate = formatter.parse(dto.getDate());
//            Date sqlDate = new Date(utilDate.getTime());
//            Time parsedTime = Time.valueOf(dto.getTime() + ":00");
//            LocalDate localDate = LocalDate.parse(dto.getDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dto.getDate(), formatter);
//            Date sqlDate = Date.valueOf(localDate);
            LocalTime localTime = LocalTime.parse(dto.getTime());

            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

            existingAppointment.setDate(localDate);
            existingAppointment.setTime(localTime);
            existingAppointment.setPatient(patient);
            existingAppointment.setDoctor(doctor);

            Appointment updated = appointmentRepository.save(existingAppointment);

            return new AppointmentResponseDTO(updated.getId(), updated.getDate(), updated.getTime(), doctor.getId(), patient.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error updating appointment: " + e.getMessage(), e);
        }
    }

    private AppointmentResponseDTO toResponseDTO(Appointment appt) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appt.getId());
        dto.setDate(appt.getDate());
        dto.setTime(appt.getTime());
        dto.setDoctorId(appt.getDoctor().getId());
        dto.setPatientId(appt.getPatient().getId());
        return dto;
    }




}


