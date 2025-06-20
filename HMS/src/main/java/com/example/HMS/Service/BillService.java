package com.example.HMS.Service;

import com.example.HMS.DTO.BillRequestDTO;
import com.example.HMS.DTO.BillResponseDTO;
import com.example.HMS.Entity.Bill;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.BillRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private PatientRepository patientRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    public BillResponseDTO createBill(BillRequestDTO dto) {
        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date utilDate = formatter.parse(dto.getDate());
//            Date sqlDate = new Date(utilDate.getTime());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dto.getDate(), formatter);
            Date sqlDate = Date.valueOf(localDate);

            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            Bill bill = new Bill();
            bill.setAmount(dto.getAmount());
            bill.setDate(localDate);
            bill.setBillDetail(dto.getBillDetail());
            bill.setStatus(dto.getStatus());
            bill.setPatient(patient);

            Bill saved = billRepository.save(bill);
            return toResponseDTO(saved);

        } catch (Exception e) {
            throw new RuntimeException("Error creating bill: " + e.getMessage(), e);
        }
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

    public BillResponseDTO updateBill(int id, BillRequestDTO dto) {
        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date utilDate = formatter.parse(dto.getDate());
//            Date sqlDate = new Date(utilDate.getTime());
//            LocalDate localDate = LocalDate.parse(dto.getDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dto.getDate(), formatter);
            Bill existingBill = billRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + id));
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));
            existingBill.setAmount(dto.getAmount());
            existingBill.setDate(localDate);
            existingBill.setBillDetail(dto.getBillDetail());
            existingBill.setStatus(dto.getStatus());
            existingBill.setPatient(patient);
            Bill updated = billRepository.save(existingBill);
            return toResponseDTO(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating bill: " + e.getMessage(), e);
        }
    }


    public List<BillResponseDTO> getBillsByPatientId(int patientId) {
        return billRepository.findByPatientId(patientId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private BillResponseDTO toResponseDTO(Bill bill) {
        BillResponseDTO dto = new BillResponseDTO();
        dto.setId(bill.getId());
        dto.setAmount(bill.getAmount());
        dto.setDate(bill.getDate());
        dto.setBillDetail(bill.getBillDetail());
        dto.setStatus(bill.getStatus());
        dto.setPatientId(bill.getPatient().getId());
        return dto;
    }
}

