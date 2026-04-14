package com.hospital_thingy.DTO;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(VitalSignDTO.class),
        @JsonSubTypes.Type(ImagingDTO.class)
})
public abstract class MedicalRecordDTO {
    public Long id;
    public LocalDateTime dateTime;
    public String notes;
    public Long appointmentId;

    protected MedicalRecordDTO() {
    }

    protected MedicalRecordDTO(Long id, LocalDateTime dateTime, String notes, Long appointmentId) {
        this.id = id;
        this.dateTime = dateTime;
        this.notes = notes;
        this.appointmentId = appointmentId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
}