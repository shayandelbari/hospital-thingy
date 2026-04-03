package com.hospital_thingy.service;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.DoctorDTO;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.DuplicateFoundException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.mapper.DoctorMapper;
import com.hospital_thingy.repository.AppointmentRepository;
import com.hospital_thingy.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServices {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;

    public DoctorServices(DoctorRepository doctorRepository, DoctorMapper doctorMapper,
            AppointmentMapper appointmentMapper, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.appointmentMapper = appointmentMapper;
        this.appointmentRepository = appointmentRepository;
    }

    // SELECT all doctors
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(doctorMapper::toDto).toList();
    }

    public List<AppointmentDTO> getDoctorAppointments(Long doctorId) {
        if (doctorRepository.findById(doctorId).isEmpty()) {
            throw new EntityNotFoundException("Doctor with id " + doctorId + " not found");
        } else {
            return appointmentRepository.findByDoctorId(doctorId).stream().map(appointmentMapper::toDto).toList();
        }
    }

    // CRUD Doctor
    public DoctorDTO createDoctor(DoctorDTO doctor) {
        try {
            doctorExists(doctor);
            throw new DuplicateFoundException("Doctor already exists");
        } catch (EntityNotFoundException e) {
            var savedDoctor = doctorRepository.save(doctorMapper.toEntity(doctor));
            return doctorMapper.toDto(savedDoctor);
        }

    }

    @Transactional
    public DoctorDTO deleteDoctor(DoctorDTO doctor) {
        try {
            var dExists = doctorExists(doctor);

            if (!dExists.appointmentIds.isEmpty()) {
                throw new DeletionFailedException("Doctor has appointments, cannot delete.");
            }

            doctorRepository.deleteById(dExists.id);
            return dExists;
        } catch (EntityNotFoundException e) {
            throw new DeletionFailedException("Doctor doesn't exist.");
        }
    }

    @Transactional
    public DoctorDTO updateDoctor(DoctorDTO doctor) {
        try {
            var dExists = doctorExists(doctor);
            dExists.firstName = doctor.firstName;
            dExists.lastName = doctor.lastName;
            dExists.specialities = doctor.specialities;

            var updatedDoctor = doctorRepository.save(doctorMapper.toEntity(dExists));
            return doctorMapper.toDto(updatedDoctor);
        } catch (EntityNotFoundException e) {
            throw new EntityUpdateException("Doctor doesn't exist.");

        }
    }

    public DoctorDTO doctorExists(DoctorDTO doctor) {
        var existingDoctor = doctorRepository.findByLicenseNumber(doctor.licenseNumber);
        if (existingDoctor.isPresent()) {
            return doctorMapper.toDto(existingDoctor.get());
        } else {
            throw new EntityNotFoundException("Doctor doesn't exist in the current data");
        }
    }

}
