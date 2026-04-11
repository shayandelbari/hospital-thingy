package com.hospital_thingy.controller;

import java.util.List;

import com.hospital_thingy.DTO.VitalSignDTO;
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
    public MedicalRecordDTO addVitalSign(@RequestBody VitalSignDTO record) {
        return medicalRecordService.createMedicalRecord(record);

    }
    //B/c we are expecting a viatal sign DTO in the above, if we had other children classed to POST, we would need them to each have unique identifiers
    // b/c that is not the case, i left the annotation as the default

}