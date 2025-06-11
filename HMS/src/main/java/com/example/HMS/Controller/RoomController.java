package com.example.HMS.Controller;

import com.example.HMS.DTO.RoomRequestDTO;
import com.example.HMS.Entity.Room;
import com.example.HMS.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomRequestDTO dto) {
        Room room = new Room();
        room.setType(dto.getType());
        room.setCapacity(dto.getCapacity());
        room.setPrice(dto.getPrice());
        return ResponseEntity.ok(roomService.saveRoom(room));
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable int id) {
        Room room = roomService.getRoomById(id);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}

