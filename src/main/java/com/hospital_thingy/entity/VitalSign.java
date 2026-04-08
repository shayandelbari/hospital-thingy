package com.hospital_thingy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class VitalSign extends MedicalRecord {
    private Integer weight;
    private Integer heartRate;
    private Integer systolicBP;
    private Integer diastolicBP;
    private Integer temperature;
    private Integer o2Saturation;
    // these are type Integer and not the primative type int, as Integer is
    // nullable, which is important for the VitalSign class

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

    public VitalSign() {
        super();
    }

    public VitalSign(String notes) {
        super(notes);
    }

    public VitalSign(String notes, Integer weight, Integer heartRate, Integer systolicBP,
            Integer diastolicBP, Integer temperature, Integer o2Saturation) {
        super(notes);
        this.weight = weight;
        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.temperature = temperature;
        this.o2Saturation = o2Saturation;
    }

    @PrePersist
    @PreUpdate
    private void validateState() {
        if (weight != null && weight < 0) {
            throw new IllegalStateException("VitalSign weight cannot be negative");
        }
        if (heartRate != null && heartRate < 0) {
            throw new IllegalStateException("VitalSign heartRate cannot be negative");
        }
        if (systolicBP != null && systolicBP < 0) {
            throw new IllegalStateException("VitalSign systolicBP cannot be negative");
        }
        if (diastolicBP != null && diastolicBP < 0) {
            throw new IllegalStateException("VitalSign diastolicBP cannot be negative");
        }
        if (systolicBP != null && diastolicBP != null && diastolicBP > systolicBP) {
            throw new IllegalStateException("VitalSign diastolicBP cannot be greater than systolicBP");
        }
        if (o2Saturation != null && (o2Saturation < 0 || o2Saturation > 100)) {
            throw new IllegalStateException("VitalSign o2Saturation must be between 0 and 100");
        }
    }
}
