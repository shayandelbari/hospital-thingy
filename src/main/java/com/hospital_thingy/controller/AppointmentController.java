package com.hospital_thingy.controller;

import java.util.List;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.service.AppointmentServices;

/**
 * Represents the API controller layer for appointment endpoints.
 * Delegates request handling to {@link AppointmentServices} and returns DTO
 * responses for client interactions.
 *
 * @author Abdulrahman Mousa
 * @version 1.0
 * @since 2026-04-09
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentServices appointmentService;

    public AppointmentController(AppointmentServices appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAll();
    }

    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointment) {
        return appointmentService.createAppointment(appointment);
    }

    @GetMapping("/search")
    public Object getAppointmentByQueryParam(@RequestParam(required = false) Long appointmentId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) LocalDate date) {
        if (appointmentId != null) {
            return appointmentService.getById(appointmentId);
        }

        if (doctorId != null && date != null) {
            return appointmentService.getDoctorAppointmentsByDate(doctorId, date);
        }

        throw new EntityCreationException("Provide either appointmentId, or both doctorId and date");
    }

    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable Long id) {
        return appointmentService.getById(id);
    }

    @PostMapping("/{id}/reschedule")
    public AppointmentDTO rescheduleAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointment) {
        return appointmentService.rescheduleAppointment(id, appointment);
    }

    @PostMapping("/{id}/start")
    public AppointmentDTO startAppointment(@PathVariable Long id) {
        return appointmentService.startAppointment(id);
    }

    @PostMapping("/{id}/complete")
    public AppointmentDTO completeAppointment(@PathVariable Long id) {
        return appointmentService.completeAppointment(id);
    }

    @PostMapping("/{id}/cancel")
    public AppointmentDTO cancelAppointment(@PathVariable Long id) {
        return appointmentService.cancelAppointment(id);
    }

    @PostMapping("/{id}/postpone")
    public AppointmentDTO postponeAppointment(@PathVariable Long id) {
        return appointmentService.postponeAppointment(id);
    }

    @GetMapping("/{id}/medical-records")
    public List<MedicalRecordDTO> getMedicalRecords(@PathVariable Long id) {
        return appointmentService.getAppointmentMedicalRecords(id);
    }

}