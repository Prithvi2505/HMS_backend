package com.example.HMS.Controller;

import com.example.HMS.DTO.RoomRequestDTO;
import com.example.HMS.DTO.RoomResponseDTO;
import com.example.HMS.Entity.Room;
import com.example.HMS.Service.RoomService;
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

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public RoomResponseDTO createRoom(@Valid @RequestBody RoomRequestDTO dto) {
        return roomService.createRoom(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<RoomResponseDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public RoomResponseDTO getRoomById(@PathVariable int id) {
        return roomService.getRoomById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public RoomResponseDTO updateRoom(@Valid @PathVariable int id, @RequestBody RoomRequestDTO dto) {
        return roomService.updateRoom(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public void deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
    }
}

