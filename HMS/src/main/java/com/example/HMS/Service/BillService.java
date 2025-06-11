package com.example.HMS.Service;

import com.example.HMS.Entity.Bill;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.BillRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Bill saveBill(Bill bill, int patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        bill.setPatient(patient);
        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(int id) {
        return billRepository.findById(id).orElse(null);
    }

    public void deleteBill(int id) {
        billRepository.deleteById(id);
    }
}

