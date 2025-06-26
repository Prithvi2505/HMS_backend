package com.example.HMS.Controller;

import com.example.HMS.DTO.RoomRequestDTO;
import com.example.HMS.DTO.RoomResponseDTO;
import com.example.HMS.DTO.RoomWithStaffDTO;
import com.example.HMS.Entity.Room;
import com.example.HMS.Service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Operation(summary = "Creating Room")
    @PostMapping()
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public RoomResponseDTO createRoom(@Valid @RequestBody RoomRequestDTO dto) {
        return roomService.createRoom(dto);
    }

    @Operation(summary = "Getting List of All Room")
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<RoomResponseDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @Operation(summary = "Getting Room by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public RoomResponseDTO getRoomById(@PathVariable int id) {
        return roomService.getRoomById(id);
    }

    @Operation(summary = "Updating Room by ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public RoomResponseDTO updateRoom(@Valid @PathVariable int id, @RequestBody RoomRequestDTO dto) {
        return roomService.updateRoom(id, dto);
    }
    @Operation(summary = "Getting Room by Staff ID")
    @GetMapping("/by-staff/{staffId}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<RoomWithStaffDTO> getRoomsByStaff(@PathVariable int staffId) {
        return roomService.getRoomsAssignedToStaff(staffId);
    }
    @Operation(summary = "Deleting Room by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public void deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
    }
}

