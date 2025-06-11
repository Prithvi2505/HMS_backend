package com.example.HMS.Service;

import com.example.HMS.DTO.StaffRequestDTO;
import com.example.HMS.DTO.StaffResponseDTO;
import com.example.HMS.Entity.Room;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Repository.RoomRepository;
import com.example.HMS.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public StaffResponseDTO createStaff(StaffRequestDTO dto) {
        Staff staff = new Staff(dto.getName(), dto.getEmail(), dto.getGender(), dto.getType(), dto.getPassword());
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

    public void deleteStaff(int id) {
        staffRepository.deleteById(id);
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

