package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.VitalSignDTO;
import com.hospital_thingy.entity.VitalSign;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VitalSignMapper {

    VitalSignDTO toDto(VitalSign vitalSign);

    VitalSign toEntity(VitalSignDTO vitalSignDTO);

    List<VitalSignDTO> toDtoList(List<VitalSign> vitalSigns);

    List<VitalSign> toEntityList(List<VitalSignDTO> vitalSignDTOs);
}