package com.hospital_thingy.service;

import com.hospital_thingy.mapper.PatientMapper;
import com.hospital_thingy.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientServices {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServices(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    // Example: convert a patient DTO into entity, persist it, and convert it back.
    // public PatientDTO createPatient(PatientDTO request) {
    // var patient = patientMapper.toEntity(request);
    // var savedPatient = patientRepository.save(patient);
    // return patientMapper.toDto(savedPatient);
    // }
}
