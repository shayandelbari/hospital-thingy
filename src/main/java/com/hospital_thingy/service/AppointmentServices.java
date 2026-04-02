package com.hospital_thingy.service;

import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServices {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentServices(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    // Example: map appointment DTO to entity and back around repository operations.
    // public AppointmentDTO createAppointment(AppointmentDTO request) {
    // var appointment = appointmentMapper.toEntity(request);
    // var savedAppointment = appointmentRepository.save(appointment);
    // return appointmentMapper.toDto(savedAppointment);
    // }
}
