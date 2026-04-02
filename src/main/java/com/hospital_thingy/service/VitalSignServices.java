package com.hospital_thingy.service;

import com.hospital_thingy.mapper.VitalSignMapper;
import com.hospital_thingy.repository.VitalSignRepository;
import org.springframework.stereotype.Service;

@Service
public class VitalSignServices {
    private final VitalSignRepository vitalSignRepository;
    private final VitalSignMapper vitalSignMapper;

    public VitalSignServices(VitalSignRepository vitalSignRepository, VitalSignMapper vitalSignMapper) {
        this.vitalSignRepository = vitalSignRepository;
        this.vitalSignMapper = vitalSignMapper;
    }

    // Example: map DTO -> entity -> repository -> DTO for vital signs.
    // public VitalSignDTO createVitalSign(VitalSignDTO request) {
    // var vitalSign = vitalSignMapper.toEntity(request);
    // var savedVitalSign = vitalSignRepository.save(vitalSign);
    // return vitalSignMapper.toDto(savedVitalSign);
    // }
}
