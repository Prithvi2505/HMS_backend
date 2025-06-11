package com.example.HMS.Service;

import com.example.HMS.Entity.Appointment;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.AppointmentRepository;
import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Appointment saveAppointment(Appointment appointment, int patientId, int doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(int id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }
}

