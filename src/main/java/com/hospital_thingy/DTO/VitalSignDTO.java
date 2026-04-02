package com.hospital_thingy.DTO;

import java.time.LocalDateTime;

public class VitalSignDTO extends MedicalRecordDTO {
    public Integer weight;
    public Integer heartRate;
    public Integer systolicBP;
    public Integer diastolicBP;
    public Integer temperature;
    public Integer o2Saturation;

    public VitalSignDTO() {
    }

    public VitalSignDTO(Long id, LocalDateTime dateTime, String notes, Integer weight, Integer heartRate,
            Integer systolicBP, Integer diastolicBP, Integer temperature, Integer o2Saturation) {
        super(id, dateTime, notes);
        this.weight = weight;
        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.temperature = temperature;
        this.o2Saturation = o2Saturation;
    }
}