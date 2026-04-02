package com.hospital_thingy.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;
    private String notes;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // This will be done in the constructor instead
    //
    //public void setDateTime(LocalDateTime dateTime) {
    //    this.dateTime = dateTime;
    //}

    public MedicalRecord(String notes) {
        this.notes = notes;
        this.dateTime = LocalDateTime.now();
    }

    public MedicalRecord() {
        this.dateTime = LocalDateTime.now();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}


