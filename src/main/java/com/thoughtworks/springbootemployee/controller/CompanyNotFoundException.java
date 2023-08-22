package com.thoughtworks.springbootemployee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Employee not found.");
    }
}