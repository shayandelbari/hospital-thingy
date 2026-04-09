package com.hospital_thingy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Doctor;
import com.hospital_thingy.entity.Patient;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.DuplicateFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.repository.AppointmentRepository;
import com.hospital_thingy.repository.DoctorRepository;
import com.hospital_thingy.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
class AppointmentServicesTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentServices appointmentServices;

    @Test
    void createAppointmentAllowsBoundaryTouchingTimes() {
        AppointmentDTO request = new AppointmentDTO();
        request.patientId = 1L;
        request.doctorId = 10L;
        request.date = LocalDate.of(2026, 4, 10);
        request.startTime = LocalTime.of(11, 0);
        request.endTime = LocalTime.of(12, 0);

        Doctor doctor = mock(Doctor.class);
        when(doctor.getId()).thenReturn(10L);

        Patient patient = new Patient("P", "Q", LocalDate.of(2000, 1, 1), 123L, "INS-1");

        Appointment mapped = new Appointment();
        Appointment saved = new Appointment();
        saved.setDate(request.date);
        saved.setStartTime(request.startTime);
        saved.setEndTime(request.endTime);
        saved.setStatus(Appointment.Status.UPCOMING);
        saved.setDoctor(doctor);
        saved.setPatient(patient);

        AppointmentDTO response = new AppointmentDTO();
        response.status = Appointment.Status.UPCOMING;

        when(doctorRepository.findById(10L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.existsDoctorDoubleBooking(
                eq(10L),
                eq(request.date),
                eq(request.startTime),
                eq(request.endTime),
                eq(null),
                any())).thenReturn(false);
        when(appointmentMapper.toEntity(request)).thenReturn(mapped);
        when(appointmentRepository.save(mapped)).thenReturn(saved);
        when(appointmentMapper.toDto(saved)).thenReturn(response);

        AppointmentDTO result = appointmentServices.createAppointment(request);

        assertEquals(Appointment.Status.UPCOMING, result.status);
        verify(appointmentRepository).save(mapped);
    }

    @Test
    void createAppointmentThrowsWhenDoctorIsDoubleBooked() {
        AppointmentDTO request = new AppointmentDTO();
        request.patientId = 1L;
        request.doctorId = 10L;
        request.date = LocalDate.of(2026, 4, 10);
        request.startTime = LocalTime.of(11, 0);
        request.endTime = LocalTime.of(12, 0);

        Doctor doctor = mock(Doctor.class);
        when(doctor.getId()).thenReturn(10L);

        Patient patient = new Patient("P", "Q", LocalDate.of(2000, 1, 1), 123L, "INS-1");

        when(doctorRepository.findById(10L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.existsDoctorDoubleBooking(
                eq(10L),
                eq(request.date),
                eq(request.startTime),
                eq(request.endTime),
                eq(null),
                any())).thenReturn(true);

        assertThrows(DuplicateFoundException.class, () -> appointmentServices.createAppointment(request));
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void startAppointmentRequiresUpcomingStatus() {
        Appointment appointment = new Appointment();
        appointment.setStatus(Appointment.Status.POSTPONED);

        when(appointmentRepository.findById(5L)).thenReturn(Optional.of(appointment));

        assertThrows(EntityUpdateException.class, () -> appointmentServices.startAppointment(5L));
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void completeAppointmentRequiresInProgressStatus() {
        Appointment appointment = new Appointment();
        appointment.setStatus(Appointment.Status.UPCOMING);

        when(appointmentRepository.findById(7L)).thenReturn(Optional.of(appointment));

        assertThrows(EntityUpdateException.class, () -> appointmentServices.completeAppointment(7L));
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void cancelAppointmentRejectsInProgressStatus() {
        Appointment appointment = new Appointment();
        appointment.setStatus(Appointment.Status.IN_PROGRESS);

        when(appointmentRepository.findById(9L)).thenReturn(Optional.of(appointment));

        assertThrows(DeletionFailedException.class, () -> appointmentServices.cancelAppointment(9L));
        verify(appointmentRepository, never()).save(any());
    }
}


