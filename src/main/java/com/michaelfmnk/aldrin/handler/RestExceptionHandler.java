package com.michaelfmnk.aldrin.handler;

import com.michaelfmnk.aldrin.dto.ErrorDetail;
import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.utils.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {
    @Autowired
    TimeProvider timeProvider;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(timeProvider.now().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(e.getMessage());
        errorDetail.setDeveloperMessage(e.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);

    }
}
