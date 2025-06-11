package com.example.HMS.Service;

import com.example.HMS.DTO.BillRequestDTO;
import com.example.HMS.DTO.BillResponseDTO;
import com.example.HMS.Entity.Bill;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.BillRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private PatientRepository patientRepository;

    public BillResponseDTO createBill(BillRequestDTO dto) {
        Bill bill = new Bill();
        bill.setAmount(dto.getAmount());
        bill.setDate(dto.getDate());
        bill.setBillDetail(dto.getBillDetail());
        bill.setPatient(patientRepository.findById(dto.getPatientId()).orElseThrow());
        return toResponseDTO(billRepository.save(bill));
    }

    public List<BillResponseDTO> getAllBills() {
        return billRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public BillResponseDTO getBillById(int id) {
        return toResponseDTO(billRepository.findById(id).orElseThrow());
    }

    public void deleteBill(int id) {
        billRepository.deleteById(id);
    }

    private BillResponseDTO toResponseDTO(Bill bill) {
        BillResponseDTO dto = new BillResponseDTO();
        dto.setId(bill.getId());
        dto.setAmount(bill.getAmount());
        dto.setDate(bill.getDate());
        dto.setBillDetail(bill.getBillDetail());
        dto.setPatientId(bill.getPatient().getId());
        return dto;
    }
}

