package com.hospital_thingy.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, updatable = false)
    private LocalDate dateOfBirth;
    private Long phoneNumber;

    @Column(nullable = false, unique = true, updatable = false)
    private String insuranceNumber;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean status;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public Patient() {
    }

    public Patient(String firstName, String lastName, LocalDate dateOfBirth,
            Long phoneNumber, String insuranceNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insuranceNumber = insuranceNumber;
        this.status = true;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    @PrePersist
    @PreUpdate
    private void validateState() {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalStateException("Patient firstName is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalStateException("Patient lastName is required");
        }

        if (id == null) {
            if (dateOfBirth == null) {
                throw new IllegalStateException("Patient dateOfBirth is required");
            }
            if (insuranceNumber == null || insuranceNumber.isBlank()) {
                throw new IllegalStateException("Patient insuranceNumber is required");
            }
        }

        if (status == null && id == null) {
            throw new IllegalStateException("Patient status is required");
        }
    }
}
