package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "medicalRecordIds", source = "medicalRecords")
    AppointmentDTO toDto(Appointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "medicalRecords", ignore = true)
    Appointment toEntity(AppointmentDTO appointmentDTO);

    List<AppointmentDTO> toDtoList(List<Appointment> appointments);

    List<Appointment> toEntityList(List<AppointmentDTO> appointmentDTOs);

    default Long map(MedicalRecord medicalRecord) {
        return medicalRecord == null ? null : medicalRecord.getId();
    }
}