package com.example.HMS.Controller;

import com.example.HMS.DTO.*;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = "http://localhost:4200")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    public StaffResponseDTO createStaff(@Valid @RequestBody StaffRequestDTO dto) {
        return staffService.createStaff(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<StaffResponseDTO> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public StaffResponseDTO getStaffById(@PathVariable int id) {
        return staffService.getStaffById(id);
    }

    @PutMapping("/assign-room/{staffId}/{roomId}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public StaffResponseDTO assignRoomToStaff(@PathVariable int staffId, @PathVariable int roomId) {
        return staffService.assignRoom(staffId, roomId);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR')")
    public StaffResponseDTO updateDoctor(@Valid @PathVariable int id, @RequestBody StaffUpdateRequestDTO dto) {
        return staffService.updateStaff(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public void deleteStaff(@PathVariable int id) {
        staffService.deleteStaff(id);
    }


}

