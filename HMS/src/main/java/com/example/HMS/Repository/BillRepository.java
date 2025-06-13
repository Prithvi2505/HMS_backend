package com.example.HMS.Repository;

import com.example.HMS.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Integer> {
    List<Bill> findByPatientId(int patientId);
}
