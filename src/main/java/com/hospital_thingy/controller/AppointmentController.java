package com.hospital_thingy.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.service.AppointmentServices;

/**
 * REST controller pattern for appointment endpoints.
 *
 * Use this as the template for new controllers:
 * - annotate the class with {@code @RestController}
 * - keep the controller thin and delegate to a service
 * - use constructor injection for required dependencies
 * - use {@code @RequestParam} for query parameters
 * - use {@code @PathVariable} for values embedded in the URL path
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

    /**
     * Example of query-parameter injection.
     *
     * Request example:
     * GET /api/appointments/search?appointmentId=10
     *
     * The value is read with {@code @RequestParam}.
     */
    @GetMapping("/search")
    public AppointmentDTO getAppointmentByQueryParam(@RequestParam Long appointmentId) {
        return appointmentService.getById(appointmentId);
    }

    /**
     * Example of dynamic URL injection.
     *
     * Request example:
     * GET /api/appointments/10
     *
     * The value is read with {@code @PathVariable}.
     */
    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable Long id) {
        return appointmentService.getById(id);
    }

}