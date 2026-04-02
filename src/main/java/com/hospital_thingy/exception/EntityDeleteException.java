package com.hospital_thingy.exception;

public class EntityDeleteException extends EntityNotFoundException {
    public EntityDeleteException(String message) { super(message); }
}
