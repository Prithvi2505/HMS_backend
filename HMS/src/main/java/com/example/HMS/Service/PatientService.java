package com.example.HMS.Service;

import com.example.HMS.DTO.PatientRequestDTO;
import com.example.HMS.DTO.PatientResponseDTO;
import com.example.HMS.DTO.PatientUpdateRequestDTO;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Entity.Room;
import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import com.example.HMS.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setMobileNo(dto.getMobileNo());
        patient.setCity(dto.getCity());
        patient.setPassword(passwordEncoder.encode(dto.getPassword()));
        return toResponseDTO(patientRepository.save(patient));
    }

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PatientResponseDTO getPatientById(int id) {
        Patient patient = patientRepository.findById(id).orElseThrow();
        return toResponseDTO(patient);
    }


    public PatientResponseDTO updatePatient(int id, PatientUpdateRequestDTO dto) {
        int loggedInUserId = authUserDetailsService.getLoggedInUserId();
        String role = authUserDetailsService.getLoggedInUserRole();

        if (role.equals("PATIENT") && id != loggedInUserId) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        Patient patient = patientRepository.findById(id).orElseThrow();

        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setMobileNo(dto.getMobileNo());
        patient.setCity(dto.getCity());

        return toResponseDTO(patientRepository.save(patient));
    }

    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    public PatientResponseDTO assignDoctor(int patientId, int doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        patient.setDoctor(doctor);
        return toResponseDTO(patientRepository.save(patient));
    }

    public PatientResponseDTO assignRoom(int patientId, int roomId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        patient.setRoom(room);
        return toResponseDTO(patientRepository.save(patient));
    }

    private PatientResponseDTO toResponseDTO(Patient patient) {
        PatientResponseDTO response = new PatientResponseDTO();
        response.setId(patient.getId());
        response.setName(patient.getName());
        response.setEmail(patient.getEmail());
        response.setAge(patient.getAge());
        response.setGender(patient.getGender());
        response.setMobileNo(patient.getMobileNo());
        response.setCity(patient.getCity());
        return response;
    }
}

