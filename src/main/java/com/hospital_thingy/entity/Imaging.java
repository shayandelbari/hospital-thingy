package com.hospital_thingy.entity;

import jakarta.persistence.Entity;

@Entity
public class Imaging extends MedicalRecord {
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Imaging() {
        super();
    }

    public Imaging(String notes) {
        super(notes);
    }

}
