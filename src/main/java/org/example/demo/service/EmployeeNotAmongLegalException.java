package org.example.demo.service;

public class EmployeeNotAmongLegalException extends RuntimeException {
    public EmployeeNotAmongLegalException(String message) {
        super(message);
    }
}
