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
import org.springframework.stereotype.Service;

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

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        Appointment appt = new Appointment();
        appt.setDate(dto.getDate());
        appt.setTime(dto.getTime());
        appt.setDoctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow());
        appt.setPatient(patientRepository.findById(dto.getPatientId()).orElseThrow());
        return toResponseDTO(appointmentRepository.save(appt));
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

    private AppointmentResponseDTO toResponseDTO(Appointment appt) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appt.getId());
        dto.setDate(appt.getDate());
        dto.setTime(appt.getTime());
        dto.setDoctorId(appt.getDoctor().getId());
        dto.setPatientId(appt.getPatient().getId());
        return dto;
    }

    public List<AppointmentResponseDTO> getAppointmentsByPatientId(int patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAppointmentsByDoctorId(int doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

}


