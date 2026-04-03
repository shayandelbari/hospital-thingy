package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.VitalSignDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.VitalSign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VitalSignMapper {

    @Mapping(target = "appointmentIds", source = "appointments")
    VitalSignDTO toDto(VitalSign vitalSign);

    @Mapping(target = "appointments", ignore = true)
    VitalSign toEntity(VitalSignDTO vitalSignDTO);

    List<VitalSignDTO> toDtoList(List<VitalSign> vitalSigns);

    List<VitalSign> toEntityList(List<VitalSignDTO> vitalSignDTOs);

    default Long map(Appointment appointment) {
        return appointment == null ? null : appointment.getId();
    }
}