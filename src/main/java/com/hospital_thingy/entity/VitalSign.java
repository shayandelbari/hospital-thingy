package com.hospital_thingy.entity;

import jakarta.persistence.Entity;

@Entity
public class VitalSign extends MedicalRecord {
    private Integer weight;
    private Integer heartRate;
    private Integer systolicBP;
    private Integer diastolicBP;
    private Integer temperature;
    private Integer o2Saturation;
    //these are type Integer and not the primative type int, as Integer is nullable, which is important for the VitalSign class

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
}
