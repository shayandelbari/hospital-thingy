package com.hospital_thingy.controller;

import java.util.List;

import com.hospital_thingy.entity.Doctor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.DoctorDTO;
import com.hospital_thingy.service.DoctorServices;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorServices doctorService;

    public DoctorController(DoctorServices doctorService) {
        this.doctorService = doctorService;
    }

    // Query parameter use case: filter doctors by speciality.
    // GET /api/doctors?speciality=CARDIOLOGY
    @GetMapping
    public List<DoctorDTO> getAllDoctors(@RequestParam(required = false) Doctor.Speciality speciality) {
        var doctors = doctorService.getAllDoctors();
        if (speciality == null) {
            return doctors;
        }

        return doctors.stream()
                .filter(doctor -> doctor.specialities != null && doctor.specialities.contains(speciality))
                .toList();
    }

    // Dynamic URL example: GET /api/doctors/10/appointments
    @GetMapping("/{doctorId}/appointments")
    public List<AppointmentDTO> getDoctorAppointmentsByPathVariable(@PathVariable Long doctorId) {
        return doctorService.getDoctorAppointments(doctorId);
    }
}