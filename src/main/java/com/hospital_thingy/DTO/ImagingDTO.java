package com.hospital_thingy.DTO;

import java.time.LocalDateTime;

public class ImagingDTO extends MedicalRecordDTO {
    public String description;

    public ImagingDTO() {
    }

    public ImagingDTO(Long id, LocalDateTime dateTime, String notes, String description) {
        super(id, dateTime, notes);
        this.description = description;
    }
}