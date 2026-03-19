package com.hospital_thingy.entity;

import java.time.LocalDate;
import java.util.List;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private long phoneNumber;
    private String insuranceNumber;

    private List<Appointment> appointments;
}
