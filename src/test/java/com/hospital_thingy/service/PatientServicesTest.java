package com.hospital_thingy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.DTO.PatientDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.MedicalRecord;
import com.hospital_thingy.entity.Patient;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.DuplicateFoundException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.mapper.MedicalRecordMapper;
import com.hospital_thingy.mapper.PatientMapper;
import com.hospital_thingy.repository.PatientRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PatientServicesTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private PatientServices patientServices;

    @Test
    void getAllPatientsReturnsAllPatientsFromRepository() {
        Patient patientOne = new Patient();
        Patient patientTwo = new Patient();
        List<Patient> patients = List.of(patientOne, patientTwo);
        PatientDTO dtoOne = new PatientDTO();
        PatientDTO dtoTwo = new PatientDTO();

        when(patientRepository.findAll()).thenReturn(patients);
        when(patientMapper.toDto(patientOne)).thenReturn(dtoOne);
        when(patientMapper.toDto(patientTwo)).thenReturn(dtoTwo);

        List<PatientDTO> result = patientServices.getAllPatients();

        assertEquals(List.of(dtoOne, dtoTwo), result);
        verify(patientRepository).findAll();
        verify(patientMapper).toDto(patientOne);
        verify(patientMapper).toDto(patientTwo);
    }

    @Test
    void getPatientAppointmentsReturnsMappedAppointmentsForPatient() {
        Long patientId = 42L;
        Appointment appointment = new Appointment();
        List<Appointment> appointments = List.of(appointment);
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(new Patient()));
        when(patientRepository.getPatientAppointments(patientId)).thenReturn(appointments);
        when(appointmentMapper.toDto(appointment)).thenReturn(appointmentDTO);

        List<AppointmentDTO> result = patientServices.getPatientAppointments(patientId);

        assertEquals(List.of(appointmentDTO), result);
        verify(patientRepository).findById(patientId);
        verify(patientRepository).getPatientAppointments(patientId);
        verify(appointmentMapper).toDto(appointment);
    }

    @Test
    void getPatientAppointmentsThrowsWhenPatientDoesNotExist() {
        Long patientId = 42L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> patientServices.getPatientAppointments(patientId));

        verify(patientRepository).findById(patientId);
        verify(patientRepository, never()).getPatientAppointments(any());
    }

    @Test
    void getPatientMedicalRecordReturnsMappedRecordsForPatient() {
        Long patientId = 42L;
        MedicalRecord medicalRecord = new MedicalRecord() {
        };
        List<MedicalRecord> medicalRecords = List.of(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO() {
        };

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(new Patient()));
        when(patientRepository.getPatientMedicalRecords(patientId)).thenReturn(medicalRecords);
        when(medicalRecordMapper.toDto(medicalRecord)).thenReturn(medicalRecordDTO);

        List<MedicalRecordDTO> result = patientServices.getPatientMedicalRecord(patientId);

        assertEquals(List.of(medicalRecordDTO), result);
        verify(patientRepository).findById(patientId);
        verify(patientRepository).getPatientMedicalRecords(patientId);
        verify(medicalRecordMapper).toDto(medicalRecord);
    }

    @Test
    void createPatientReturnsExistingPatientWhenNotFoundLookupFails() {
        PatientDTO request = new PatientDTO();
        request.insuranceNumber = "INS-1";
        Patient entity = new Patient();

        when(patientRepository.findByInsuranceNumber(request.insuranceNumber)).thenReturn(Optional.empty());
        when(patientMapper.toEntity(request)).thenReturn(entity);
        when(patientRepository.save(entity)).thenReturn(entity);

        PatientDTO result = patientServices.createPatient(request);

        assertSame(request, result);
        verify(patientRepository).save(entity);
    }

    @Test
    void createPatientThrowsDuplicateWhenExistingPatientIsActive() {
        PatientDTO request = new PatientDTO();
        request.insuranceNumber = "INS-1";
        Patient existingEntity = new Patient();
        existingEntity.setStatus(true);
        PatientDTO existingDto = new PatientDTO();

        when(patientRepository.findByInsuranceNumber(request.insuranceNumber)).thenReturn(Optional.of(existingEntity));
        when(patientMapper.toDto(existingEntity)).thenReturn(existingDto);
        when(patientMapper.toEntity(existingDto)).thenReturn(existingEntity);

        assertThrows(DuplicateFoundException.class, () -> patientServices.createPatient(request));

        verify(patientRepository, never()).save(any());
    }

    @Test
    void deletePatientDeletesWhenPatientExists() {
        PatientDTO request = new PatientDTO();
        request.insuranceNumber = "INS-1";
        request.id = 7L;
        Patient existingEntity = new Patient();
        PatientDTO existingDto = new PatientDTO();
        existingDto.id = 7L;

        when(patientRepository.findByInsuranceNumber(request.insuranceNumber)).thenReturn(Optional.of(existingEntity));
        when(patientMapper.toDto(existingEntity)).thenReturn(existingDto);

        PatientDTO result = patientServices.deletePatient(request);

        assertSame(request, result);
        verify(patientRepository).deleteById(7L);
    }

    @Test
    void deletePatientThrowsWhenPatientDoesNotExist() {
        PatientDTO request = new PatientDTO();
        request.insuranceNumber = "INS-1";

        when(patientRepository.findByInsuranceNumber(request.insuranceNumber)).thenReturn(Optional.empty());

        assertThrows(DeletionFailedException.class, () -> patientServices.deletePatient(request));

        verify(patientRepository, never()).deleteById(any());
    }

    @Test
    void updatePatientReturnsUpdatedDtoWhenPatientExists() {
        PatientDTO request = new PatientDTO();
        request.insuranceNumber = "INS-1";
        request.firstName = "New";
        request.lastName = "Name";
        request.phoneNumber = 123L;

        Patient existingEntity = new Patient();
        existingEntity.setFirstName("Old");
        existingEntity.setLastName("Value");
        existingEntity.setPhoneNumber(999L);

        PatientDTO existingDto = new PatientDTO();
        existingDto.firstName = "Old";
        existingDto.lastName = "Value";
        existingDto.phoneNumber = 999L;

        Patient updatedEntity = new Patient();
        updatedEntity.setFirstName("Old");
        updatedEntity.setLastName("Value");
        updatedEntity.setPhoneNumber(999L);
        PatientDTO updatedDto = new PatientDTO();
        updatedDto.firstName = "New";
        updatedDto.lastName = "Name";
        updatedDto.phoneNumber = 123L;

        when(patientRepository.findByInsuranceNumber(request.insuranceNumber)).thenReturn(Optional.of(existingEntity));
        when(patientMapper.toDto(existingEntity)).thenReturn(existingDto);
        when(patientMapper.toEntity(existingDto)).thenReturn(updatedEntity);
        when(patientMapper.toDto(updatedEntity)).thenReturn(updatedDto);

        PatientDTO result = patientServices.updatePatient(request);

        assertEquals("New", result.firstName);
        assertEquals("Name", result.lastName);
        assertEquals(123L, result.phoneNumber);
        verify(patientMapper).toEntity(existingDto);
        verify(patientMapper).toDto(updatedEntity);
    }

    @Test
    void updatePatientThrowsWhenPatientDoesNotExist() {
        PatientDTO request = new PatientDTO();
        request.insuranceNumber = "INS-1";

        when(patientRepository.findByInsuranceNumber(request.insuranceNumber)).thenReturn(Optional.empty());

        assertThrows(EntityUpdateException.class, () -> patientServices.updatePatient(request));
    }
}