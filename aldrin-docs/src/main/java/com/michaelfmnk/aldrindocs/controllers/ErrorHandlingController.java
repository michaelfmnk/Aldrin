package com.michaelfmnk.aldrindocs.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@CommonsLog
@RestController
@ControllerAdvice
@AllArgsConstructor
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

}
