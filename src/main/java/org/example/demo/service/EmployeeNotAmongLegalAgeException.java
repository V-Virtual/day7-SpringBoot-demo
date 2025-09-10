package org.example.demo.service;

public class EmployeeNotAmongLegalAgeException extends RuntimeException {
    public EmployeeNotAmongLegalAgeException(String message) {
        super(message);
    }
}
