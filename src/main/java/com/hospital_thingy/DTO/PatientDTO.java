package com.hospital_thingy.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class PatientDTO {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("dateOfBirth")
    public LocalDate dateOfBirth;

    @JsonProperty("phoneNumber")
    public Long phoneNumber;

    @JsonProperty("insuranceNumber")
    public String insuranceNumber;

    @JsonProperty("status")
    public Boolean status;

    @JsonProperty("appointmentIds")
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
        this.status = true;
        this.appointmentIds = appointmentIds;
    }

    public PatientDTO(Long id, String firstName, String lastName, LocalDate dateOfBirth,
                      Long phoneNumber, String insuranceNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insuranceNumber = insuranceNumber;
        this.status = true;
    }

    public PatientDTO(String firstName, String lastName, LocalDate dateOfBirth,
                      Long phoneNumber, String insuranceNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insuranceNumber = insuranceNumber;
        this.status = true;
    }
    public PatientDTO(String firstName, String lastName,
                      Long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = null;
        this.phoneNumber = phoneNumber;
        this.insuranceNumber = null;
        this.status = true;
    }

}
