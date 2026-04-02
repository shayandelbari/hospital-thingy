package com.hospital_thingy.exception.appointment;

public class AppointmentBaseException extends RuntimeException {
    public AppointmentBaseException(String message) {
        super("Appointment Error: " + (message.isBlank() ? message : "Error occurred while handling appointment"));
    }
}
