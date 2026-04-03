package com.hospital_thingy.DTO;

import java.time.LocalDateTime;

public class ImagingDTO extends MedicalRecordDTO {
    public byte[] image;

    public ImagingDTO() {
    }

    public ImagingDTO(Long id, LocalDateTime dateTime, String notes, Long appointmentId, byte[] image) {
        super(id, dateTime, notes, appointmentId);
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}