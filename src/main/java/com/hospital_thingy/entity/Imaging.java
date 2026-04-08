package com.hospital_thingy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Imaging extends MedicalRecord {
    @Lob
    @Column(nullable = false)
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

    public Imaging(String notes, byte[] image) {
        super(notes);
        this.image = image;
    }

    @PrePersist
    @PreUpdate
    private void validateState() {
        if (image == null || image.length == 0) {
            throw new IllegalStateException("Imaging image is required and cannot be empty");
        }
    }

}
