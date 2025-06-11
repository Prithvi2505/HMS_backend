package com.example.HMS.Service;

import com.example.HMS.Entity.Room;
import com.example.HMS.Entity.Staff;
import com.example.HMS.Repository.RoomRepository;
import com.example.HMS.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(int id) {
        return staffRepository.findById(id).orElse(null);
    }

    public void deleteStaff(int id) {
        staffRepository.deleteById(id);
    }

    public Staff assignRoomToStaff(int staffId, int roomId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        staff.getRooms().add(room);
        return staffRepository.save(staff);
    }
}

