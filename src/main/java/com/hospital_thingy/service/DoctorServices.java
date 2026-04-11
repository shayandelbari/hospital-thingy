package com.hospital_thingy.service;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.DoctorDTO;
import com.hospital_thingy.entity.Doctor;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.DuplicateFoundException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.mapper.DoctorMapper;
import com.hospital_thingy.repository.AppointmentRepository;
import com.hospital_thingy.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    public DoctorDTO deleteDoctor(Long id) {
        var delDoctor = doctorRepository.findById(id);
        if (delDoctor.isEmpty()) {
            throw new DeletionFailedException("Doctor with id " + id + " does not exist.");
        }
        else {
            var doctor = delDoctor.get();
            if (doctor.getAppointments() != null && !doctor.getAppointments().isEmpty()) {
                throw new DeletionFailedException(
                        "Doctor with id " + id + " has an appointment in the records. Doctor can't be deleted.");
            }
            doctorRepository.deleteById(id);
        }
        return doctorMapper.toDto(delDoctor.get());
    }

    @Transactional
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + id + " not found"));

        // avoid duplicate license -- id stays, other properties change too, but license
        // need to be changed to update
        var doctorSameLic = doctorRepository.findByLicenseNumber(doctor.licenseNumber);
        if (doctorSameLic.isPresent() && !doctorSameLic.get().getId().equals(id)) {
            throw new DuplicateFoundException("Doctor with license " + doctor.licenseNumber + " already exists.");
        }

        existingDoctor.setFirstName(doctor.firstName);
        existingDoctor.setLastName(doctor.lastName);
        existingDoctor.setLicenseNumber(doctor.licenseNumber);
        existingDoctor.setSpecialities(doctor.specialities);

        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return doctorMapper.toDto(updatedDoctor);
    }

    public DoctorDTO getDoctorById(Long id) {
        var doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            return doctorMapper.toDto(doctor.get());
        }
        throw new EntityNotFoundException("Doctor with id " + id + " not found");
    }

    public void doctorExists(DoctorDTO doctor) {
        var existingDoctor = doctorRepository.findById(doctor.id);
        if (existingDoctor.isPresent()) {
            doctorMapper.toDto(existingDoctor.get());
        } else {
            throw new EntityNotFoundException("Doctor doesn't exist in the current data");
        }
    }

    public List<DoctorDTO> getDoctorByName(String lastName){
        Doctor probe = new Doctor();
        probe.setLastName(lastName);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<Doctor> example = Example.of(probe, matcher);

        return doctorMapper.toDtoList(doctorRepository.findAll(example));
    }

    public List<DoctorDTO> getDoctorByName(String firstName, String lastName){
        Doctor probe = new Doctor();
        probe.setLastName(lastName);
        probe.setFirstName(firstName);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<Doctor> example = Example.of(probe, matcher);

        return doctorMapper.toDtoList(doctorRepository.findAll(example));
    }



}
