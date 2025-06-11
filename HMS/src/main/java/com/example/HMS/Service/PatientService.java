package com.example.HMS.Service;

import com.example.HMS.Entity.Doctor;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Entity.Room;
import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import com.example.HMS.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(int id) {
        return patientRepository.findById(id).orElse(null);
    }

    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    public Patient assignDoctorToPatient(int patientId, int doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        patient.setDoctor(doctor);
        return patientRepository.save(patient);
    }

    public Patient assignRoomToPatient(int patientId, int roomId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        patient.setRoom(room);
        return patientRepository.save(patient);
    }
}

