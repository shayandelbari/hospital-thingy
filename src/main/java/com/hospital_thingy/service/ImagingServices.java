package com.hospital_thingy.service;

import com.hospital_thingy.mapper.ImagingMapper;
import com.hospital_thingy.repository.ImagingRepository;
import org.springframework.stereotype.Service;

@Service
public class ImagingServices {
    private final ImagingRepository imagingRepository;
    private final ImagingMapper imagingMapper;

    public ImagingServices(ImagingRepository imagingRepository, ImagingMapper imagingMapper) {
        this.imagingRepository = imagingRepository;
        this.imagingMapper = imagingMapper;
    }

    // Example: use mapper to transform ImagingDTO before and after repository work.
    // public ImagingDTO createImaging(ImagingDTO request) {
    // var imaging = imagingMapper.toEntity(request);
    // var savedImaging = imagingRepository.save(imaging);
    // return imagingMapper.toDto(savedImaging);
    // }
}
