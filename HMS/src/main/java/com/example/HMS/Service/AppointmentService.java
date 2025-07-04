package com.example.HMS.Service;

import com.example.HMS.DTO.AppointmentRequestDTO;
import com.example.HMS.DTO.AppointmentResponseDTO;
import com.example.HMS.Entity.Appointment;
import com.example.HMS.Entity.Doctor;
import com.example.HMS.Entity.Patient;
import com.example.HMS.Repository.AppointmentRepository;
import com.example.HMS.Repository.DoctorRepository;
import com.example.HMS.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private static final int MAX_SLOTS_PER_TIME = 1;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dto.getDate(), formatter);
            LocalTime localTime = LocalTime.parse(dto.getTime());

            if (!isDoctorAvailable(dto.getDoctorId(), localDate, localTime)) {
                throw new IllegalStateException("Doctor is not available at this time.");
            }
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            int count = appointmentRepository.countByDoctorIdAndDate(doctor.getId(), localDate);
            if (count >= doctor.getMaxAppointmentsPerDay()) {
                throw new IllegalStateException("Doctor has reached the appointment limit for this day.");
            }

            Appointment appointment = new Appointment();
            appointment.setDate(localDate);
            appointment.setTime(localTime);
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);

            Appointment saved = appointmentRepository.save(appointment);

            return new AppointmentResponseDTO(saved.getId(), saved.getDate(), saved.getTime(),
                    doctor.getId(), patient.getId());

        } catch (Exception e) {
            throw new RuntimeException("Error creating appointment: " + e.getMessage(), e);
        }
    }


    public int countAppointments(int doctorId, LocalDate date) {
        return appointmentRepository.countByDoctorIdAndDate(doctorId, date);
    }


    public boolean isDoctorAvailable(int doctorId, LocalDate date, LocalTime time) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        DayOfWeek day = date.getDayOfWeek();
        if (!doctor.getAvailableDays().contains(day)) {
            return false; // Doctor doesn't work on this day
        }
        if (time.isBefore(doctor.getStartTime()) || time.isAfter(doctor.getEndTime())) {
            return false; // Time is outside working hours
        }
        List<Appointment> existingAppointments =
                appointmentRepository.findByDoctorIdAndDateAndTime(doctorId, date, time);
        return existingAppointments.size() < MAX_SLOTS_PER_TIME;
    }


    public List<String> getAvailableTimeSlots(int doctorId, LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocalTime start = doctor.getStartTime();
        LocalTime end = doctor.getEndTime();
        int interval = 60;
        List<String> allSlots = new ArrayList<>();
        LocalTime time = start;
        while (!time.isAfter(end.minusMinutes(interval))) {
            allSlots.add(time.toString().substring(0, 5));
            time = time.plusMinutes(interval);
        }
        List<Appointment> bookedAppointments = appointmentRepository
                .findByDoctorIdAndDate(doctorId, date);

        Set<String> bookedTimes = bookedAppointments.stream()
                .map(a -> a.getTime().toString().substring(0, 5)) // "HH:mm"
                .collect(Collectors.toSet());

        return allSlots.stream()
                .filter(slot -> !bookedTimes.contains(slot))
                .collect(Collectors.toList());
    }




    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public AppointmentResponseDTO getAppointmentById(int id) {
        return toResponseDTO(appointmentRepository.findById(id).orElseThrow());
    }

    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }

    public List<String> getBookedTimesByDoctorAndDate(int doctorId, LocalDate date) {
        List<LocalTime> times = appointmentRepository.findTimesByDoctorIdAndDate(doctorId, date);
        return times.stream()
                .map(time -> time.format(DateTimeFormatter.ofPattern("HH:mm"))) // returns "HH:mm:ss", you can trim if needed
                .collect(Collectors.toList());
    }


    public List<AppointmentResponseDTO> getAppointmentsByPatientId(int patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAppointmentsByDoctorId(int doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
    public AppointmentResponseDTO updateAppointment(int id, AppointmentRequestDTO dto) {
        int loggedInUserId = authUserDetailsService.getLoggedInUserId();
        String role = authUserDetailsService.getLoggedInUserRole();

        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

        if (role.equals("PATIENT") && existingAppointment.getPatient().getId() != loggedInUserId) {
            throw new AccessDeniedException("You can only update your own appointment");
        }

        if (role.equals("DOCTOR") && existingAppointment.getDoctor().getId() != loggedInUserId) {
            throw new AccessDeniedException("You can only update appointments assigned to you");
        }
        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date utilDate = formatter.parse(dto.getDate());
//            Date sqlDate = new Date(utilDate.getTime());
//            Time parsedTime = Time.valueOf(dto.getTime() + ":00");
//            LocalDate localDate = LocalDate.parse(dto.getDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dto.getDate(), formatter);
//            Date sqlDate = Date.valueOf(localDate);
            LocalTime localTime = LocalTime.parse(dto.getTime());

            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

            if (!isDoctorAvailable(doctor.getId(), localDate, localTime)) {
                throw new IllegalStateException("Doctor is not available at this time.");
            }

            boolean isDateOrDoctorChanged = !localDate.equals(existingAppointment.getDate())
                    || doctor.getId() != existingAppointment.getDoctor().getId();

            if (isDateOrDoctorChanged) {
                int count = appointmentRepository.countByDoctorIdAndDate(doctor.getId(), localDate);
                if (count >= doctor.getMaxAppointmentsPerDay()) {
                    throw new IllegalStateException("Doctor has reached the appointment limit for this day.");
                }
            }

            existingAppointment.setDate(localDate);
            existingAppointment.setTime(localTime);
            existingAppointment.setPatient(patient);
            existingAppointment.setDoctor(doctor);

            Appointment updated = appointmentRepository.save(existingAppointment);

            return new AppointmentResponseDTO(updated.getId(), updated.getDate(), updated.getTime(), doctor.getId(), patient.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error updating appointment: " + e.getMessage(), e);
        }
    }

    private AppointmentResponseDTO toResponseDTO(Appointment appt) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appt.getId());
        dto.setDate(appt.getDate());
        dto.setTime(appt.getTime());
        dto.setDoctorId(appt.getDoctor().getId());
        dto.setPatientId(appt.getPatient().getId());
        return dto;
    }




}


