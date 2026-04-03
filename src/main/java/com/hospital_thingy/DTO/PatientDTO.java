package com.hospital_thingy.DTO;

import java.time.LocalDate;
import java.util.List;

public class PatientDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public LocalDate dateOfBirth;
    public Long phoneNumber;
    public String insuranceNumber;
    public Boolean status;
    public List<Long> appointmentIds;

    public PatientDTO() {
    }

    public PatientDTO(Long id, String firstName, String lastName, LocalDate dateOfBirth,
            Long phoneNumber, String insuranceNumber, List<Long> appointmentIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insuranceNumber = insuranceNumber;
        this.appointmentIds = appointmentIds;
    }
}
