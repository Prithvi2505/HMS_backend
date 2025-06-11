package com.example.HMS.Service;

import com.example.HMS.DTO.DoctorRequestDTO;
import com.example.HMS.DTO.DoctorResponseDTO;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorResponseDTO createDoctor(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor(dto.getName(), dto.getEmail(), dto.getSpecialization(), dto.getYearOfExperience(), dto.getPassword());
        return toResponseDTO(doctorRepository.save(doctor));
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public DoctorResponseDTO getDoctorById(int id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        return toResponseDTO(doctor);
    }

    public DoctorResponseDTO updateDoctor(int id, DoctorRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        doctor.setName(dto.getName());
        doctor.setEmail(dto.getEmail());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setYearOfExperience(dto.getYearOfExperience());
        doctor.setPassword(dto.getPassword());
        return toResponseDTO(doctorRepository.save(doctor));
    }

    public void deleteDoctor(int id) {
        doctorRepository.deleteById(id);
    }

    private DoctorResponseDTO toResponseDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setYearOfExperience(doctor.getYearOfExperience());
        return dto;
    }
}

