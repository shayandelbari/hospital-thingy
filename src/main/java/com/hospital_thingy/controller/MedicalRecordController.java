package com.hospital_thingy.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.service.MedicalRecordServices;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {
    private final MedicalRecordServices medicalRecordService;

    public MedicalRecordController(MedicalRecordServices medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    // Query parameter use case: filter records by appointment id.
    // GET /api/medical-records/search?appointmentId=22
    @GetMapping("/search")
    public List<MedicalRecordDTO> getMedicalRecordsByAppointment(@RequestParam Long appointmentId) {
        return medicalRecordService.getAllMedicalRecords().stream()
                .filter(record -> record.getAppointmentId() != null && record.getAppointmentId().equals(appointmentId))
                .toList();
    }

    // Dynamic URL example: GET /api/medical-records/3
    @GetMapping("/{id}")
    public List<MedicalRecordDTO> getMedicalRecordByPathVariable(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecordById(id).toList();
    }
}