package com.hospital_thingy.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;
    private String notes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // This will be done in the constructor instead
    //
    // public void setDateTime(LocalDateTime dateTime) {
    // this.dateTime = dateTime;
    // }

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

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @PrePersist
    @PreUpdate
    private void validateState() {
        if (appointment == null) {
            throw new IllegalStateException("MedicalRecord appointment is required");
        }
    }

}
