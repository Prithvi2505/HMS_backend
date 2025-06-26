package com.example.HMS.Controller;

import com.example.HMS.DTO.*;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
@CrossOrigin
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Operation(summary = "Creating Staff")
    @PostMapping()
    public StaffResponseDTO createStaff(@Valid @RequestBody StaffRequestDTO dto) {
        return staffService.createStaff(dto);
    }

    @Operation(summary = "Getting List of all Staff")
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<StaffResponseDTO> getAllStaff() {
        return staffService.getAllStaff();
    }

    @Operation(summary = "Getting Staff by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public StaffResponseDTO getStaffById(@PathVariable int id) {
        return staffService.getStaffById(id);
    }

    @Operation(summary = "Assigning a Staff To Room By Their Respective ID")
    @PutMapping("/assign-room/{staffId}/{roomId}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public StaffResponseDTO assignRoomToStaff(@PathVariable int staffId, @PathVariable int roomId) {
        return staffService.assignRoom(staffId, roomId);
    }
    @Operation(summary = "Updating Staff by ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR')")
    public StaffResponseDTO updateDoctor(@Valid @PathVariable int id, @RequestBody StaffUpdateRequestDTO dto) {
        return staffService.updateStaff(id, dto);
    }

    @Operation(summary = "Deleting Staff by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public void deleteStaff(@PathVariable int id) {
        staffService.deleteStaff(id);
    }

    @Operation(summary = "Rooming a Staff from a Room By Their Respective ID")
    @PutMapping("/unassign-room/{staffId}/{roomId}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public StaffResponseDTO unassignRoom(@PathVariable int staffId, @PathVariable int roomId) {
        return staffService.unassignRoom(staffId, roomId);
    }


}

