package com.hospital_thingy.controller;

import com.hospital_thingy.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //This class specifies what to do with our user-defined variables in case one is thrown in ALL our controllers.
    //This cleans the code by avoiding having a try-catch clause in every single service implementation in case
    //errors are thrown when using the restAPIs.

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityCreationException.class)
    public String EntityCreationException(EntityCreationException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityUpdateException.class)
    public String EntityUpdateException(EntityUpdateException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateFoundException.class)
    public String DuplicateFoundException(DuplicateFoundException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DeletionFailedException.class)
    public String DeletionFailedException(DeletionFailedException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String Exception(Exception ex){
        return ex.getMessage();
    }


    //Both function take care of 400 exceptions that are thrown
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String Exc(MethodArgumentNotValidException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String Exc(HttpMessageNotReadableException ex){
        return ex.getMessage();
    }















}
