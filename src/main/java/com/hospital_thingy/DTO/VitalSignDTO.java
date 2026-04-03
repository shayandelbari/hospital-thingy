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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getSystolicBP() {
        return systolicBP;
    }

    public void setSystolicBP(Integer systolicBP) {
        this.systolicBP = systolicBP;
    }

    public Integer getDiastolicBP() {
        return diastolicBP;
    }

    public void setDiastolicBP(Integer diastolicBP) {
        this.diastolicBP = diastolicBP;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getO2Saturation() {
        return o2Saturation;
    }

    public void setO2Saturation(Integer o2Saturation) {
        this.o2Saturation = o2Saturation;
    }
}