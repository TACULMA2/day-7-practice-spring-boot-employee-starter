package com.thoughtworks.springbootemployee.util;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Monkey D. Luffy", 19, "Male", 300000, 1L));
        employees.add(new Employee(2L, "Ronoroa Zoro", 21, "Male", 111100, 1L));
        employees.add(new Employee(3L, "Nami", 20, "Female", 36600, 1L));
        employees.add(new Employee(4L, "Ussop", 19, "Male", 50000, 1L));
        employees.add(new Employee(5L, "Vinsmoke Sanji", 21, "Male", 103200, 1L));
        employees.add(new Employee(6L, "Tony Tony Chopper", 17, "Male", 1000, 1L));
        employees.add(new Employee(7L, "Nico Robin", 30, "Female", 93000, 1L));
        employees.add(new Employee(8L, "Franky", 36, "Male", 39400, 1L));
        employees.add(new Employee(9L, "Brook", 90, "Male", 38300, 1L));
        employees.add(new Employee(10L, "Trafalgar Law", 26, "Male", 300000, 2L));
        employees.add(new Employee(11L, "Eustass Kid", 23, "Male", 300000, 3L));
        return employees;
    }
}
