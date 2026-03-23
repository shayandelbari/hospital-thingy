package com.hospital_thingy.entity;

import jakarta.persistence.Entity;

@Entity
public class VitalSign extends MedicalRecord {
    private int weight;
    private int heartRate;
    private int[] bloodPressure;
    private int temperature;
    private int o2Stats;
}
