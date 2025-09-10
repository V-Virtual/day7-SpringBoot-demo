package org.example.demo.controller;

import org.example.demo.service.EmployeeNotAmongLegalException;
import org.example.demo.service.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotAmongLegalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNotAmongLegalException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(Exception e) {
        return e.getMessage();
    }
}
