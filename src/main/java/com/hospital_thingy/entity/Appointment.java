package com.hospital_thingy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        POSTPONED, // if we decided not to update appointments and create another one as part of the "update"
        CANCELLED
    }

    private List<MedicalRecord> medicalRecords;
    public class Imaging extends MedicalRecord {
        private String description;
        private byte[] image;
    }
}

