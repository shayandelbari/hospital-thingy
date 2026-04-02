package com.hospital_thingy.DTO;

import java.time.LocalDateTime;

public class VitalSignDTO extends MedicalRecordDTO {
    public int weight;
    public int heartRate;
    public int[] bloodPressure;
    public int temperature;
    public int o2Stats;

    public VitalSignDTO() {
    }

    public VitalSignDTO(Long id, LocalDateTime dateTime, String notes, int weight, int heartRate,
            int[] bloodPressure, int temperature, int o2Stats) {
        super(id, dateTime, notes);
        this.weight = weight;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.o2Stats = o2Stats;
    }
}