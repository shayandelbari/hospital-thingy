package com.hospital_thingy.entity;

import java.util.List;

public class Doctor {
    private Integer id;
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private Speciality[] specialities;
    private List<Appointment> appointments;

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

}


