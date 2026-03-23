package com.hospital_thingy.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private Speciality[] specialities;

    public enum Speciality
    {
        CARDIOLOGY,
        DERMATOLOGY,
        NEUROLOGY,
        PEDIATRICS,
        ORTHOPEDICS,
        GYNECOLOGY,
        ONCOLOGY,
        PSYCHIATRY,
        OPHTHALMOLOGY,
        GASTROENTEROLOGY
    }


    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}


