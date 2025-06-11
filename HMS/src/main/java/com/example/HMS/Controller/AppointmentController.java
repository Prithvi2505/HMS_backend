package com.example.HMS.Controller;

import com.example.HMS.DTO.AppointmentRequestDTO;
import com.example.HMS.DTO.AppointmentResponseDTO;
import com.example.HMS.Entity.Appointment;
import com.example.HMS.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public AppointmentResponseDTO createAppointment(@RequestBody AppointmentRequestDTO dto) {
        return appointmentService.createAppointment(dto);
    }

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable int id) {
        return appointmentService.getAppointmentById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
    }
}

