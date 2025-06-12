package com.example.HMS.Repository;

import com.example.HMS.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
   Doctor findByEmail(String email);
}
