package com.pauloricardo.api.Exception;

import org.springframework.http.HttpStatus;

public class BussinessException extends RuntimeException {


    private final HttpStatus status;

    public BussinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
