package com.hospital_thingy.exception;

public class EntityBaseException extends RuntimeException{
    public EntityBaseException(String m){
        super("Entity Error: " + (m.isBlank() ? m : "Error occurred while handling entity"));
    }
}
