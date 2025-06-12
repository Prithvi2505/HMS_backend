package com.example.HMS.Service;



import com.example.HMS.DTO.LoginRequestDTO;
import com.example.HMS.Entity.AuthUserDetails;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import com.example.HMS.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired private PatientRepository patientRepo;
    @Autowired private DoctorRepository doctorRepo;
    @Autowired private StaffRepository staffRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = patientRepo.findByEmail(email);
        if (patient != null) return new AuthUserDetails(email, patient.getPassword(), "PATIENT");

        Doctor doctor = doctorRepo.findByEmail(email);
        if (doctor != null) return new AuthUserDetails(email, doctor.getPassword(), "DOCTOR");

        Staff staff = staffRepo.findByEmail(email);
        if (staff != null) return new AuthUserDetails(email, staff.getPassword(), "STAFF");

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    public int getUserIdFromRepo(LoginRequestDTO dto) {
        String email = dto.getEmail();
        String role = dto.getRole().toLowerCase();

        switch (role) {
            case "patient":
                return patientRepo.findByEmail(email).getId();
            case "doctor":
                return doctorRepo.findByEmail(email).getId();
            case "staff":
                return staffRepo.findByEmail(email).getId();
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}

