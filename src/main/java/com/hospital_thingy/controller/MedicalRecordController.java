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

    // Dynamic URL example: GET /api/medical-records/3
    @GetMapping("/{id}")
    public MedicalRecordDTO getMedicalRecordById(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecordById(id);
    }

    @PostMapping
    public void addMedicalRecord(@RequestBody MedicalRecordDTO record) {
        medicalRecordService.createMedicalRecord(record);
    }

}