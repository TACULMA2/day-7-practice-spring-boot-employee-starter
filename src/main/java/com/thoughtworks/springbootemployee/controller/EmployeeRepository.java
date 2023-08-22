package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1L, "Monkey D. Luffy", 19, "Male", 300000));
        employees.add(new Employee(2L, "Ronoroa Zoro", 21, "Male", 111100));
        employees.add(new Employee(3L, "Nami", 20, "Female", 36600));
        employees.add(new Employee(4L, "Ussop", 19, "Male", 50000));
        employees.add(new Employee(5L, "Vinsmoke Sanji", 21, "Male", 103200));
        employees.add(new Employee(6L, "Tony Tony Chopper", 17, "Male", 1000));
        employees.add(new Employee(7L, "Nico Robin", 30, "Female", 93000));
        employees.add(new Employee(8L, "Franky", 36, "Male", 39400));
        employees.add(new Employee(9L, "Brook", 90, "Male", 38300));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }
}
