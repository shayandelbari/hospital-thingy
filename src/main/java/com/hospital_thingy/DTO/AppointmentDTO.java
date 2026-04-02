package com.hospital_thingy.DTO;

import com.hospital_thingy.entity.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentDTO {
    public Long id;
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public Appointment.Status status;
    public String reasonForVisit;
    public Long patientId;
    public Long doctorId;
    public List<Long> medicalRecordIds;

    public AppointmentDTO() {
    }

    public AppointmentDTO(Long id, LocalDate date, LocalTime startTime, LocalTime endTime,
                          Appointment.Status status, String reasonForVisit, Long patientId,
                          Long doctorId, List<Long> medicalRecordIds) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.reasonForVisit = reasonForVisit;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicalRecordIds = medicalRecordIds;
    }
}