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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dto.getDate());
            Time parsedTime = Time.valueOf(dto.getTime() + ":00");
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            Appointment appointment = new Appointment();
            appointment.setDate(parsedDate);
            appointment.setTime(parsedTime);
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

        if (role.equals("PATIENT") && id != loggedInUserId) {
            throw new AccessDeniedException("You can only update your own profile");
        }
        try {
            Appointment existingAppointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dto.getDate());
            Time parsedTime = Time.valueOf(dto.getTime() + ":00");

            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

            existingAppointment.setDate(parsedDate);
            existingAppointment.setTime(parsedTime);
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


