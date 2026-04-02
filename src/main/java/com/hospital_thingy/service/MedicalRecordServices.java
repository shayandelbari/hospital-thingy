package com.hospital_thingy.service;

import com.hospital_thingy.mapper.ImagingMapper;
import com.hospital_thingy.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordServices {
    private final MedicalRecordRepository medicalRecordRepository;
    private final ImagingMapper medicalRecordMapper;

    public MedicalRecordServices(MedicalRecordRepository medicalRecordRepository, ImagingMapper medicalRecordMapper) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordMapper = medicalRecordMapper;
    }

    // Example: medical-record service can use a concrete mapper such as
    // ImagingMapper.
    // public ImagingDTO createImagingRecord(ImagingDTO request) {
    // var imaging = medicalRecordMapper.toEntity(request);
    // var savedRecord = medicalRecordRepository.save(imaging);
    // return medicalRecordMapper.toDto((com.hospital_thingy.entity.Imaging)
    // savedRecord);
    // }
}
