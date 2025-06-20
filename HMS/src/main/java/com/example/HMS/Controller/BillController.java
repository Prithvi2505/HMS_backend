package com.example.HMS.Controller;

import com.example.HMS.DTO.BillRequestDTO;
import com.example.HMS.DTO.BillResponseDTO;
import com.example.HMS.Entity.Bill;
import com.example.HMS.Service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
@CrossOrigin
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public BillResponseDTO createBill(@Valid @RequestBody BillRequestDTO dto) {
        return billService.createBill(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT','STAFF')")
    public List<BillResponseDTO> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT','STAFF')")
    public BillResponseDTO getBillById(@PathVariable int id) {
        return billService.getBillById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public void deleteBill(@PathVariable int id) {
        billService.deleteBill(id);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT','STAFF')")
    public List<BillResponseDTO> getBillsByPatient(@PathVariable int patientId) {
        return billService.getBillsByPatientId(patientId);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public BillResponseDTO updateBill(@PathVariable int id, @RequestBody BillRequestDTO dto) {
        return billService.updateBill(id, dto);
    }

}

