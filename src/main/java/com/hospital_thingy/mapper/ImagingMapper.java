package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.ImagingDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Imaging;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImagingMapper {

    @Mapping(target = "appointmentIds", source = "appointments")
    ImagingDTO toDto(Imaging imaging);

    @Mapping(target = "appointments", ignore = true)
    Imaging toEntity(ImagingDTO imagingDTO);

    List<ImagingDTO> toDtoList(List<Imaging> imagingRecords);

    List<Imaging> toEntityList(List<ImagingDTO> imagingDTOs);

    default Long map(Appointment appointment) {
        return appointment == null ? null : appointment.getId();
    }
}