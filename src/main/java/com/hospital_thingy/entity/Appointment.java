package com.hospital_thingy.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Appointment {
    private int id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Status status;
    private String reasonForVisit;

    public enum Status {
        UPCOMING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

    private List<MedicalRecord> medicalRecords;
}

