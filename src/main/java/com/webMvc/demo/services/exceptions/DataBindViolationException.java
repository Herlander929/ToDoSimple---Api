package com.webMvc.demo.services.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataBindViolationException extends DataIntegrityViolationException{

    public DataBindViolationException(String message){
        super(message);

    }
    
}