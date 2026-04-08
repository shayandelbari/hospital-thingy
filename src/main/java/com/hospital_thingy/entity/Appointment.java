package com.hospital_thingy.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String reasonForVisit;

    public enum Status {
        UPCOMING,
        IN_PROGRESS,
        COMPLETED,
        POSTPONED, // if we decided not to update appointments and create another one as part of
                   // the "update"
        CANCELLED
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;

    public Appointment() {
    }

    public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime,
            Status status, String reasonForVisit) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.reasonForVisit = reasonForVisit;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    @PrePersist
    @PreUpdate
    private void validateState() {
        if (date == null) {
            throw new IllegalStateException("Appointment date is required");
        }
        if (startTime == null) {
            throw new IllegalStateException("Appointment startTime is required");
        }
        if (endTime == null) {
            throw new IllegalStateException("Appointment endTime is required");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalStateException("Appointment endTime must be after startTime");
        }
        if (patient == null) {
            throw new IllegalStateException("Appointment patient is required");
        }
        if (doctor == null) {
            throw new IllegalStateException("Appointment doctor is required");
        }
    }
}
