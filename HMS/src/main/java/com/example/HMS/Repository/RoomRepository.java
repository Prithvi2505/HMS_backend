package com.example.HMS.Repository;

import com.example.HMS.Entity.Appointment;
import com.example.HMS.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {
}
