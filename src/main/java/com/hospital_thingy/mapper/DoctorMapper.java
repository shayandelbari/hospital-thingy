package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.DoctorDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "appointmentIds", source = "appointments")
    DoctorDTO toDto(Doctor doctor);

    @Mapping(target = "appointments", ignore = true)
    Doctor toEntity(DoctorDTO doctorDTO);

    List<DoctorDTO> toDtoList(List<Doctor> doctors);

    List<Doctor> toEntityList(List<DoctorDTO> doctorDTOs);

    default Long map(Appointment appointment) {
        return appointment == null ? null : appointment.getId();
    }
}