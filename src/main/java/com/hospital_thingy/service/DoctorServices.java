package com.hospital_thingy.service;

import com.hospital_thingy.entity.Doctor;
import com.hospital_thingy.mapper.DoctorMapper;
import com.hospital_thingy.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServices {

    @Autowired
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorServices(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public List<Doctor> getAllDoctors()
    {
        return doctorRepository.findAll();
    }

    public void AddDoctor(Doctor doctor)
    {
        doctorRepository.save(doctor);
    }

    public void UpdateDoctor(Doctor doctor)
    {
        doctorRepository.save(doctor);
    }

    public void DeleteDoctor(Doctor doctor)
    {
        doctorRepository.delete(doctor);
    }

    



    // Example: map incoming DTO to entity, save with repository, then map back to
    // DTO.
    // public DoctorDTO createDoctor(DoctorDTO request) {
    // var doctor = doctorMapper.toEntity(request);
    // var savedDoctor = doctorRepository.save(doctor);
    // return doctorMapper.toDto(savedDoctor);
    // }
}
