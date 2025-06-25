package com.example.HMS.Service;

import com.example.HMS.Entity.AuthUserDetails;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import com.example.HMS.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        // Try to find user in Patient repo
        Patient patient = patientRepo.findByEmail(email);
        if (patient != null) {
            return new AuthUserDetails(email, patient.getPassword(), "patient", patient.getId());
        }

        // Try Doctor repo
        Doctor doctor = doctorRepo.findByEmail(email);
        if (doctor != null) {
            return new AuthUserDetails(email, doctor.getPassword(), "doctor", doctor.getId());
        }

        // Try Staff repo
        Staff staff = staffRepo.findByEmail(email);
        if (staff != null) {
            return new AuthUserDetails(email, staff.getPassword(), "staff", staff.getId());
        }

        // Not found
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    public int getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) auth.getPrincipal();
        return userDetails.getUserId();
    }

    /**
     * Get logged-in user Role from Security Context
     */
    public String getLoggedInUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) auth.getPrincipal();
        return userDetails.getRole(); // PATIENT / DOCTOR / STAFF
    }
}
