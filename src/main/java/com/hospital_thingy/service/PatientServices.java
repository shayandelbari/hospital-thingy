package com.hospital_thingy.service;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.DTO.PatientDTO;
import com.hospital_thingy.entity.Patient;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.DuplicateFoundException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.mapper.MedicalRecordMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.hospital_thingy.mapper.PatientMapper;
import com.hospital_thingy.repository.PatientRepository;

import java.time.LocalDate;
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
        var pExists = patientRepository.findByInsuranceNumber(patient.insuranceNumber);
        if (pExists.isPresent()) {
            var pExistsEntity = pExists.get(); // because its Optional, I need to take the actual value
            if (pExistsEntity.getStatus()) {
                throw new DuplicateFoundException("Patient already exists and it's active");
            }
            // Inactive patient so its status is turned to true
            pExistsEntity.setStatus(true);
            return patientMapper.toDto(patientRepository.save(pExistsEntity));
        } else {
            Patient newPatient = new Patient(patient.firstName, patient.lastName, patient.dateOfBirth,
                    patient.phoneNumber, patient.insuranceNumber);
            var addedPatient = patientRepository.save(newPatient);
            return patientMapper.toDto(addedPatient);
        }

    }

    @Transactional
    public PatientDTO deletePatient(Long id) {
        var pExists = patientRepository.findById(id);
        if (pExists.isEmpty()) {
            throw new DeletionFailedException("Patient to be deleted doesn't exist.");
        } else {
            var pExistsEntity = pExists.get();
            if (pExistsEntity.getAppointments().isEmpty()) {
                patientRepository.deleteById(id);
            } else {
                pExistsEntity.setStatus(false);
            }
            return patientMapper.toDto(pExistsEntity);

        }
    }

    @Transactional
    public PatientDTO updatePatient(Long id, PatientDTO patient) {
        var existingP = patientRepository.findById(id);
        if (existingP.isEmpty()) {
            throw new EntityUpdateException("Patient to be updated doesn't exist");
        } else {
            var existingPEntity = existingP.get();
            Patient updatedP = new Patient(id, patient.firstName, patient.lastName,
                    existingPEntity.getDateOfBirth(), patient.phoneNumber,
                    existingPEntity.getInsuranceNumber());
            patientRepository.save(updatedP);
            return patientMapper.toDto(updatedP);
        }
    }

    @Transactional
    public PatientDTO updatePatientStatus(Long id, Boolean status) {
        var existingP = patientRepository.findById(id);
        if (existingP.isEmpty()) {
            throw new EntityUpdateException("Patient to be updated doesn't exist");
        } else {
            var existingPEntity = existingP.get();
            existingPEntity.setStatus(status);
            patientRepository.save(existingPEntity);
            return patientMapper.toDto(existingPEntity);
        }
    }

    // isPresent() returns true or false before actually calling .get() on the
    // object preventing nullPointerExceptions
    // That's why I don't use isEmpty()
    public PatientDTO getPatientById(Long id) {
        var patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            return patientMapper.toDto(patient.get());
        } else {
            throw new EntityNotFoundException("Patient with id " + id + " doesn't exist.");
        }
    }

    public List<PatientDTO> getPatientByDOB(LocalDate dob){
        return patientMapper.toDtoList(patientRepository.findByDateOfBirth(dob));
    }

    public PatientDTO getPatientByInsuranceNumber(String insuranceNumber){
        return patientMapper.toDto(patientRepository.findByInsuranceNumber(insuranceNumber)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find any patients with this Insurance Number")));
    }

    public List<PatientDTO> getPatientByName(String lastName){
        Patient probe = new Patient();
        probe.setLastName(lastName);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<Patient> example = Example.of(probe, matcher);

        return patientMapper.toDtoList(patientRepository.findAll(example));
    }

    public List<PatientDTO> getPatientByName(String firstName,  String lastName) {
        Patient probe = new Patient();
        probe.setLastName(lastName);
        probe.setFirstName(firstName);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<Patient> example = Example.of(probe, matcher);

        return patientMapper.toDtoList(patientRepository.findAll(example));
    }
}