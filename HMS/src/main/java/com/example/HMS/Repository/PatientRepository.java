package com.example.HMS.Repository;

import com.example.HMS.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Patient findByEmail(String email);
    List<Patient> findByNameContainingIgnoreCase(String name);
}
