package com.thoughtworks.springbootemployee.exception;

public class EmployeeActiveStatusException extends RuntimeException {
    public EmployeeActiveStatusException() {
        super("Employee is inactive.");
    }
}
