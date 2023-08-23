package com.thoughtworks.springbootemployee.exception;

public class EmployeeCreateException extends RuntimeException{

    public EmployeeCreateException() {
        super("Employees must be 18-65 years old.");
    }

}
