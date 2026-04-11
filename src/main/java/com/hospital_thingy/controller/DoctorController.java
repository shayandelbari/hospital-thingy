package com.hospital_thingy.controller;

import java.util.List;

import com.hospital_thingy.entity.Doctor;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.exception.EntityUpdateException;
import org.springframework.web.bind.annotation.*;

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

    // get doctor by id
    @GetMapping("/{id}")
    public DoctorDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/search")
    public List<DoctorDTO> search(@RequestParam(required = false) String firstName, @RequestParam String lastName) {
        if (firstName != null) {
            return doctorService.getDoctorByName(firstName, lastName);
        }
        return doctorService.getDoctorByName(lastName);
    }

    // create doctor
    @PostMapping
    public DoctorDTO createDoctor(@RequestBody DoctorDTO doctorDTO) {
        validateDoctor(doctorDTO, false);
        return doctorService.createDoctor(doctorDTO);
    }

    // update doctor
    @PutMapping("/{id}")
    public DoctorDTO updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        validateDoctor(doctorDTO, true);
        return doctorService.updateDoctor(id, doctorDTO);

    }

    @DeleteMapping("/{id}")
    public DoctorDTO deleteDoctor(@PathVariable Long id) {

        return doctorService.deleteDoctor(id);
    }

    // Dynamic URL example: GET /api/doctors/10/appointments
    @GetMapping("/{doctorId}/appointments")
    public List<AppointmentDTO> getDoctorAppointments(@PathVariable Long doctorId) {
        return doctorService.getDoctorAppointments(doctorId);
    }

    // throws exception if name, last name and license are empty.
    private void validateDoctor(DoctorDTO doctorDTO, boolean isUpdate) {
        if (doctorDTO.firstName == null || doctorDTO.firstName.isBlank()
                || doctorDTO.lastName == null || doctorDTO.lastName.isBlank()
                || doctorDTO.licenseNumber == null || doctorDTO.licenseNumber.isBlank()) {
            if (isUpdate) {
                throw new EntityUpdateException("First name, last name, and license number are required.");
            } else {
                throw new EntityCreationException("First name, last name, and license number are required.");
            }

        }
    }
}