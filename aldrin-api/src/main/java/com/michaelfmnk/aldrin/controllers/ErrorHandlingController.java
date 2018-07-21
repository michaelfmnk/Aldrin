package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.services.utils.TimeProvider;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
@AllArgsConstructor
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    private final TimeProvider timeProvider;

}
