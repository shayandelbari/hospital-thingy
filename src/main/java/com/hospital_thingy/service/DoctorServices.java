package com.hospital_thingy.service;

import com.hospital_thingy.mapper.DoctorMapper;
import com.hospital_thingy.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorServices {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorServices(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    // Example: map incoming DTO to entity, save with repository, then map back to
    // DTO.
    // public DoctorDTO createDoctor(DoctorDTO request) {
    // var doctor = doctorMapper.toEntity(request);
    // var savedDoctor = doctorRepository.save(doctor);
    // return doctorMapper.toDto(savedDoctor);
    // }
}
