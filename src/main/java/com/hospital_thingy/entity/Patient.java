package com.hospital_thingy.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;
    private Long phoneNumber;

    @Column(nullable = false, unique = true)
    private String insuranceNumber;

    @OneToMany(mappedBy = "patient",  cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
