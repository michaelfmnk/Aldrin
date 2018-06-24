package com.michaelfmnk.aldrin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SuchUserAlreadyExitsException extends RuntimeException {
    public SuchUserAlreadyExitsException(){

    }
    public SuchUserAlreadyExitsException(String msg){
        super(msg);
    }
    public SuchUserAlreadyExitsException(String msg, Throwable cause){
        super(msg, cause);
    }
}
