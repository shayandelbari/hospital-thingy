package com.hospital_thingy.DTO;

import com.hospital_thingy.entity.Doctor;

import java.util.Collection;
import java.util.List;

public class DoctorDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String licenseNumber;
    public Collection<Doctor.Speciality> specialities;
    public List<Long> appointmentIds;

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String firstName, String lastName, String licenseNumber,
                     Collection<Doctor.Speciality> specialities, List<Long> appointmentIds) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.specialities = specialities;
        this.appointmentIds = appointmentIds;
    }
}
