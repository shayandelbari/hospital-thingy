package com.hospital_thingy.entity;

import jakarta.persistence.Entity;

@Entity
public class Imaging extends MedicalRecord {
    private String description;
    private byte[] image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
