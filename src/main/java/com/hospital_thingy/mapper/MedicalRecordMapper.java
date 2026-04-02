package com.hospital_thingy.mapper;

import com.hospital_thingy.DTO.ImagingDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.DTO.VitalSignDTO;
import com.hospital_thingy.entity.Imaging;
import com.hospital_thingy.entity.MedicalRecord;
import com.hospital_thingy.entity.VitalSign;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {

    private final VitalSignMapper vitalSignMapper;
    private final ImagingMapper imagingMapper;

    public MedicalRecordMapper(VitalSignMapper vitalSignMapper, ImagingMapper imagingMapper) {
        this.vitalSignMapper = vitalSignMapper;
        this.imagingMapper = imagingMapper;
    }

    public MedicalRecordDTO toDto(MedicalRecord record) {

        if (record instanceof VitalSign vs) {
            return vitalSignMapper.toDto(vs);
        }

        if (record instanceof Imaging img) {
            return imagingMapper.toDto(img);
        }

        throw new IllegalArgumentException("Unknown type");
    }

    public MedicalRecord toEntity(MedicalRecordDTO dto) {
        if (dto instanceof VitalSignDTO vs) {
            return vitalSignMapper.toEntity(vs);
        }

        if (dto instanceof ImagingDTO img) {
            return imagingMapper.toEntity(img);
        }

        throw new IllegalArgumentException("Unknown type");
    }
}