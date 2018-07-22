package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.dtos.ErrorDetailDto;
import com.michaelfmnk.aldrin.exceptions.BadRequestException;
import com.michaelfmnk.aldrin.services.utils.TimeProvider;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;


@CommonsLog
@RestController
@ControllerAdvice
@AllArgsConstructor
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    private final TimeProvider timeProvider;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetailDto exceptionHandler(Exception e) {
        ErrorDetailDto errorDetailDto = ErrorDetailDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
        return errorDetailDto;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetailDto exceptionHandler(BadRequestException e) {
        ErrorDetailDto errorDetailDto = ErrorDetailDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
        return errorDetailDto;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetailDto exceptionHandler(EntityNotFoundException e) {
        ErrorDetailDto errorDetailDto = ErrorDetailDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
        return errorDetailDto;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetailDto exceptionHandler(AccessDeniedException e) {
        ErrorDetailDto errorDetailDto = ErrorDetailDto.builder()
                .status(HttpStatus.FORBIDDEN)
                .cause(e)
                .timeStamp(timeProvider.getDate())
                .build();
        return errorDetailDto;
    }


}
