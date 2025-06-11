package com.example.HMS.Controller;

import com.example.HMS.DTO.BillRequestDTO;
import com.example.HMS.Entity.Bill;
import com.example.HMS.Service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody BillRequestDTO dto) {
        Bill bill = new Bill();
        bill.setAmount(dto.getAmount());
        bill.setDate(dto.getDate());
        bill.setBillDetail(dto.getBillDetail());
        return ResponseEntity.ok(billService.saveBill(bill, dto.getPatientId()));
    }

    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable int id) {
        Bill bill = billService.getBillById(id);
        return bill != null ? ResponseEntity.ok(bill) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable int id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}

