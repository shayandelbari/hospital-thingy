package com.hospital_thingy.entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

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

    public enum Speciality {
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

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String licenseNumber,
            Collection<Speciality> specialities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.specialities = specialities;
    }

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

    @PrePersist
    @PreUpdate
    private void validateState() {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalStateException("Doctor firstName is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalStateException("Doctor lastName is required");
        }
        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalStateException("Doctor licenseNumber is required");
        }
    }
}
