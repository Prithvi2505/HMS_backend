package com.example.HMS.Service;

import com.example.HMS.DTO.DoctorRequestDTO;
import com.example.HMS.DTO.DoctorResponseDTO;
import com.example.HMS.DTO.DoctorUpdateRequestDTO;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private AuthUserDetailsService authUserDetailsService;

    public DoctorResponseDTO createDoctor(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setEmail(dto.getEmail());
        doctor.setGender(dto.getGender());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setYearOfExperience(dto.getYearOfExperience());
        doctor.setMaxAppointmentsPerDay(dto.getMaxAppointmentsPerDay());
        // Encrypt password
        doctor.setPassword(passwordEncoder.encode(dto.getPassword()));
        return toResponseDTO(doctorRepository.save(doctor));
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public DoctorResponseDTO getDoctorById(int id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        return toResponseDTO(doctor);
    }

    public DoctorResponseDTO updateDoctor(int id, DoctorUpdateRequestDTO dto) {
        int loggedInUserId = authUserDetailsService.getLoggedInUserId();
        String role = authUserDetailsService.getLoggedInUserRole();

        if (role.equals("DOCTOR") && id != loggedInUserId) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        doctor.setName(dto.getName());
        doctor.setGender(dto.getGender());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setYearOfExperience(dto.getYearOfExperience());
        doctor.setMaxAppointmentsPerDay(dto.getMaxAppointmentsPerDay());
        return toResponseDTO(doctorRepository.save(doctor));
    }

    public void deleteDoctor(int id) {
        int loggedInUserId = authUserDetailsService.getLoggedInUserId();
        String role = authUserDetailsService.getLoggedInUserRole();

        if (!role.equals("DOCTOR") || id != loggedInUserId) {
            throw new AccessDeniedException("You can only delete your own doctor profile");
        }

        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("Doctor with id " + id + " not found");
        }

        doctorRepository.deleteById(id);
    }

    private DoctorResponseDTO toResponseDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setGender(doctor.getGender());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setYearOfExperience(doctor.getYearOfExperience());
        dto.setMaxAppointmentsPerDay(doctor.getMaxAppointmentsPerDay());
        return dto;
    }
}

