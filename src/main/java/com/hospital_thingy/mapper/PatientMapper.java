package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.PatientDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "appointmentIds", source = "appointments")
    PatientDTO toDto(Patient patient);

    @Mapping(target = "appointments", ignore = true)
    //Needed for correct mapping
    @Mapping(target = "status", source = "status")
    Patient toEntity(PatientDTO patientDTO);

    List<PatientDTO> toDtoList(List<Patient> patients);

    List<Patient> toEntityList(List<PatientDTO> patientDTOs);

    default Long map(Appointment appointment) {
        return appointment == null ? null : appointment.getId();
    }
}