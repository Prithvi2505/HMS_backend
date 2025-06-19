package com.example.HMS.Service;

import com.example.HMS.DTO.*;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Entity.Room;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Repository.RoomRepository;
import com.example.HMS.Repository.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Autowired private AuthUserDetailsService authUserDetailsService;


    public StaffResponseDTO createStaff(StaffRequestDTO dto) {
        Staff staff = new Staff();
        staff.setName(dto.getName());
        staff.setEmail(dto.getEmail());
        staff.setType(dto.getType());
        staff.setGender(dto.getGender());
        // Encrypt password
        staff.setPassword(passwordEncoder.encode(dto.getPassword()));
        return toResponseDTO(staffRepository.save(staff));
    }

    public List<StaffResponseDTO> getAllStaff() {
        return staffRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public StaffResponseDTO getStaffById(int id) {
        Staff staff = staffRepository.findById(id).orElseThrow();
        return toResponseDTO(staff);
    }

    public StaffResponseDTO assignRoom(int staffId, int roomId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        staff.getRooms().add(room);
        return toResponseDTO(staffRepository.save(staff));
    }
    public StaffResponseDTO updateStaff(int id, StaffUpdateRequestDTO dto) {
        int loggedInUserId = authUserDetailsService.getLoggedInUserId();
        String role = authUserDetailsService.getLoggedInUserRole();

        if (role.equals("STAFF") && id != loggedInUserId) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        Staff staff = staffRepository.findById(id).orElseThrow();

        staff.setName(dto.getName());
        staff.setGender(dto.getGender());
        staff.setType(dto.getType());

        return toResponseDTO(staffRepository.save(staff));
    }

    public void deleteStaff(int id) {
        int loggedInUserId = authUserDetailsService.getLoggedInUserId();
        String role = authUserDetailsService.getLoggedInUserRole();
        if (role.equals("STAFF") && id != loggedInUserId) {
            throw new AccessDeniedException("You can only delete your own staff profile");
        }
        if (!role.equals("STAFF") && !role.equals("DOCTOR")) {
            throw new AccessDeniedException("Only staff or doctors can delete staff profiles");
        }
        if (!staffRepository.existsById(id)) {
            throw new EntityNotFoundException("Staff with id " + id + " not found");
        }
        staffRepository.deleteById(id);
    }
    public StaffResponseDTO unassignRoom(int staffId, int roomId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        if (!staff.getRooms().contains(room)) {
            throw new IllegalStateException("Staff is not assigned to this room");
        }
        staff.getRooms().remove(room);
        room.getStaff().remove(staff); // Optional: maintain bidirectional sync
        return toResponseDTO(staffRepository.save(staff));
    }

    private StaffResponseDTO toResponseDTO(Staff staff) {
        StaffResponseDTO dto = new StaffResponseDTO();
        dto.setId(staff.getId());
        dto.setName(staff.getName());
        dto.setEmail(staff.getEmail());
        dto.setGender(staff.getGender());
        dto.setType(staff.getType());
        return dto;
    }
}

