package com.example.HMS.Service;

import com.example.HMS.DTO.RoomRequestDTO;
import com.example.HMS.DTO.RoomResponseDTO;
import com.example.HMS.Entity.Room;
import com.example.HMS.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

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

    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
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

