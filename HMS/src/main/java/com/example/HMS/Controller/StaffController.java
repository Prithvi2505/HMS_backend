package com.example.HMS.Controller;

import com.example.HMS.DTO.StaffRequestDTO;
import com.example.HMS.DTO.StaffResponseDTO;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    public StaffResponseDTO createStaff(@RequestBody StaffRequestDTO dto) {
        return staffService.createStaff(dto);
    }

    @GetMapping
    public List<StaffResponseDTO> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public StaffResponseDTO getStaffById(@PathVariable int id) {
        return staffService.getStaffById(id);
    }

    @PutMapping("/assign-room/{staffId}/{roomId}")
    public StaffResponseDTO assignRoomToStaff(@PathVariable int staffId, @PathVariable int roomId) {
        return staffService.assignRoom(staffId, roomId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStaff(@PathVariable int id) {
        staffService.deleteStaff(id);
    }
}

