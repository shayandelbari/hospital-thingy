package com.hospital_thingy.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        POSTPONED, // if we decided not to update appointments and create another one as part of the "update"
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


}

