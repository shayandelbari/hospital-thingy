package com.hospital_thingy.DTO;

import java.time.LocalDateTime;

public abstract class MedicalRecordDTO {
    public Long id;
    public LocalDateTime dateTime;
    public String notes;

    protected MedicalRecordDTO() {
    }

    protected MedicalRecordDTO(Long id, LocalDateTime dateTime, String notes) {
        this.id = id;
        this.dateTime = dateTime;
        this.notes = notes;
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
}