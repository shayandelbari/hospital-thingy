package com.hospital_thingy.service;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.DTO.PatientDTO;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.DuplicateFoundException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.mapper.MedicalRecordMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.hospital_thingy.mapper.PatientMapper;
import com.hospital_thingy.repository.PatientRepository;

import java.util.List;

@Service
public class PatientServices {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AppointmentMapper appointmentMapper;
    private final MedicalRecordMapper medicalRecordMapper;

    public PatientServices(PatientRepository patientRepository, PatientMapper patientMapper,
            AppointmentMapper appointmentMapper, MedicalRecordMapper medicalRecordMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.appointmentMapper = appointmentMapper;
        this.medicalRecordMapper = medicalRecordMapper;
    }

    // SELECT everything
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(patientMapper::toDto).toList();
    }

    // SELECT patients appointment by patient id
    public List<AppointmentDTO> getPatientAppointments(Long id) {
        if (patientRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Patient with id " + id + " doesn't exist.");
        } else {
            return patientRepository.getPatientAppointments(id).stream().map(appointmentMapper::toDto).toList();
        }
    }

    // SELECT patients medical records by patient id
    public List<MedicalRecordDTO> getPatientMedicalRecord(Long id) {
        if (patientRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Patient with id " + id + " doesn't exist.");
        } else {
            return patientRepository.getPatientMedicalRecords(id).stream().map(medicalRecordMapper::toDto).toList();
        }
    }

    // ADD
    // returns a value for later handling in view. Like showing created patient
    public PatientDTO createPatient(PatientDTO patient) {
        try {
            var pExists = patientMapper.toEntity(patientExists(patient));
            if (pExists.getStatus()) {
                throw new DuplicateFoundException("Patient already exists and it's active");
            }
            pExists.setStatus(true);
            var updatedP = patientRepository.save(pExists);
            return patientMapper.toDto(updatedP);
        } catch (EntityNotFoundException e) {
            patientRepository.save(patientMapper.toEntity(patient));
            return patient;
        }
    }

    @Transactional
    public PatientDTO deletePatient(PatientDTO patient) {
        try {
            var pExists = patientExists(patient);
            patientRepository.deleteById(pExists.id);
            return patient;

        } catch (EntityNotFoundException e) {
            throw new DeletionFailedException("Patient doesn't exist.");
        }
    }

    @Transactional
    public PatientDTO updatePatient(PatientDTO patient) {
        try {
            var existingP = patientExists(patient);
            var patientToUpdate = patientMapper.toEntity(existingP);
            patientToUpdate.setFirstName(patient.firstName);
            patientToUpdate.setLastName(patient.lastName);
            patientToUpdate.setPhoneNumber(patient.phoneNumber);
            return patientMapper.toDto(patientToUpdate);
        } catch (EntityNotFoundException e) {
            throw new EntityUpdateException("Patient to be updated doesn't exist");
        }
    }

    // function for reusability
    public PatientDTO patientExists(PatientDTO patient) {
        var existingPatient = patientRepository.findByInsuranceNumber(patient.insuranceNumber);
        if (existingPatient.isPresent()) {
            return patientMapper.toDto(existingPatient.get());
        } else {
            throw new EntityNotFoundException("Patient doesn't exist in the current data");
        }
    }

}