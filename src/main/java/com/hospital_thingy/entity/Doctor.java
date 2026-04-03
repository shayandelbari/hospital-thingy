package com.hospital_thingy.entity;

import jakarta.persistence.*;

import java.util.Collection;
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

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Collection<Speciality> specialities;

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
        GASTROENTEROLOGY,
        OTORHINOLARYNGOLOGY
    }


    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Collection<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Collection<Speciality> specialities) {
        this.specialities = specialities;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}


