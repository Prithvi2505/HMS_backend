package com.example.HMS.Controller;

import com.example.HMS.DTO.AppointmentRequestDTO;
import com.example.HMS.DTO.AppointmentResponseDTO;
import com.example.HMS.Entity.Appointment;
import com.example.HMS.Service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "Creating Appointment")
    @PostMapping()
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDTO dto) {
        try {
            return ResponseEntity.ok(appointmentService.createAppointment(dto));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
//    public AppointmentResponseDTO createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
//        return appointmentService.createAppointment(dto);
//    }

    @Operation(summary = "Getting the count of Appointment done of a paticular Doctor")
    @GetMapping("/count")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<Integer> getCount(
            @RequestParam int doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.countAppointments(doctorId, date));
    }



    @Operation(summary = "Getting All Appointment")
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @Operation(summary = "Getting Appointment by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public AppointmentResponseDTO getAppointmentById(@PathVariable int id) {
        return appointmentService.getAppointmentById(id);
    }

    @Operation(summary = "Checking avaliability of doctor on a specific time and date ")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> isDoctorAvailable(
            @RequestParam int doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        boolean available = appointmentService.isDoctorAvailable(doctorId, date, time);
        return ResponseEntity.ok(available);
    }


    @Operation(summary = "Deleting Appointment by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
    }

    @Operation(summary = "Getting Appointment Based on Patient ID")
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<AppointmentResponseDTO> getAppointmentsByPatient(@PathVariable int patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @Operation(summary = "Getting Appointment Based on Doctor ID")
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(@PathVariable int doctorId) {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    @Operation(summary = "Getting Booked time of a specific doctor on a specific date")
    @GetMapping("/{doctorId}/{date}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<List<String>> getDoctorAppointmentsForDate(
            @PathVariable int doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<String> bookedTimes = appointmentService.getBookedTimesByDoctorAndDate(doctorId, date);
        return ResponseEntity.ok(bookedTimes);
    }

    @Operation(summary = "Getting available time slot of a specific doctor on a specific date")
    @GetMapping("/available-timeslots/{doctorId}/{date}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<List<String>> getAvailableTimeSlots(
            @PathVariable int doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<String> slots = appointmentService.getAvailableTimeSlots(doctorId, date);
        return ResponseEntity.ok(slots);
    }


    @Operation(summary = "Updating Appointment By ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public AppointmentResponseDTO updateAppointment(@PathVariable int id, @RequestBody AppointmentRequestDTO dto) {
        return appointmentService.updateAppointment(id, dto);
    }
}

