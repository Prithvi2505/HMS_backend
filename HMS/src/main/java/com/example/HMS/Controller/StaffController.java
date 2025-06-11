package com.example.HMS.Controller;

import com.example.HMS.DTO.StaffRequestDTO;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody StaffRequestDTO dto) {
        Staff staff = new Staff(dto.getName(), dto.getEmail(), dto.getGender(), dto.getType(), dto.getPassword());
        return ResponseEntity.ok(staffService.saveStaff(staff));
    }

    @GetMapping
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable int id) {
        Staff staff = staffService.getStaffById(id);
        return staff != null ? ResponseEntity.ok(staff) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable int id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{staffId}/assign-room")
    public ResponseEntity<Staff> assignRoom(@PathVariable int staffId, @RequestParam int roomId) {
        return ResponseEntity.ok(staffService.assignRoomToStaff(staffId, roomId));
    }
}

