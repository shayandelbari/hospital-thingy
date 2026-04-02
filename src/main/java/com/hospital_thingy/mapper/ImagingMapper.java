package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.ImagingDTO;
import com.hospital_thingy.entity.Imaging;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImagingMapper {

    ImagingDTO toDto(Imaging imaging);

    Imaging toEntity(ImagingDTO imagingDTO);

    List<ImagingDTO> toDtoList(List<Imaging> imagingRecords);

    List<Imaging> toEntityList(List<ImagingDTO> imagingDTOs);
}