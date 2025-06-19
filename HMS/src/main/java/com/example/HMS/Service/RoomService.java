package com.example.HMS.Service;

import com.example.HMS.DTO.RoomRequestDTO;
import com.example.HMS.DTO.RoomResponseDTO;
import com.example.HMS.DTO.RoomWithStaffDTO;
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
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StaffRepository staffRepository;

    public RoomResponseDTO createRoom(RoomRequestDTO dto) {
        Room room = new Room();
        room.setType(dto.getType());
        room.setCapacity(dto.getCapacity());
        room.setPrice(dto.getPrice());
        return toResponseDTO(roomRepository.save(room));
    }

    public List<RoomResponseDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public RoomResponseDTO getRoomById(int id) {
        Room room = roomRepository.findById(id).orElseThrow();
        return toResponseDTO(room);
    }

    public RoomResponseDTO updateRoom(int id, RoomRequestDTO dto) {
        Room room = roomRepository.findById(id).orElseThrow();
        room.setType(dto.getType());
        room.setCapacity(dto.getCapacity());
        room.setPrice(dto.getPrice());
        return toResponseDTO(roomRepository.save(room));
    }

    public void deleteRoom(int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.getStaff().forEach(staff -> staff.getRooms().remove(room));
        room.getStaff().clear();
        roomRepository.save(room);
        roomRepository.deleteById(roomId);
    }

    public List<RoomWithStaffDTO> getRoomsAssignedToStaff(int staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        return staff.getRooms().stream()
                .map(room -> new RoomWithStaffDTO(
                        room.getId(),
                        room.getType(),
                        room.getCapacity(),
                        room.getPrice(),
                        staffId
                ))
                .collect(Collectors.toList());
    }

    private RoomResponseDTO toResponseDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setId(room.getId());
        dto.setType(room.getType());
        dto.setCapacity(room.getCapacity());
        dto.setPrice(room.getPrice());
        return dto;
    }
}

