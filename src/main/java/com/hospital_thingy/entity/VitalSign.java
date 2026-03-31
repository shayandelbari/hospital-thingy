package com.hospital_thingy.entity;

import jakarta.persistence.Entity;

@Entity
public class VitalSign extends MedicalRecord {
    private int weight;
    private int heartRate;
    private int[] bloodPressure;
    private int temperature;
    private int o2Stats;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int[] getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int[] bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getO2Stats() {
        return o2Stats;
    }

    public void setO2Stats(int o2Stats) {
        this.o2Stats = o2Stats;
    }
}
