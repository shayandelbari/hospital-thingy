package com.hospital_thingy.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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
    public MedicalRecordDTO getMedicalRecordById(@RequestParam Long recordId) {
        return medicalRecordService.getMedicalRecordById(recordId);
    }

    @PostMapping
    public void addMedicalRecord(@RequestBody MedicalRecordDTO record) {
       medicalRecordService.createMedicalRecord(record);
    }

}