package com.thoughtworks.springbootemployee.controller;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    private static final List<Employee> employees = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Straw Hat"));
        companies.add(new Company(2L, "Heart Pirates"));
        companies.add(new Company(3L, "Kid Pirates"));
    }

    static {
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
        employees.add(new Employee(10L, "Eustass Kid", 23, "Male", 300000, 3L));
    }

    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findById(Long companyId) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Company> findByCompanyName(String companyName) {
        return companies.stream()
                .filter(employee -> employee.getCompanyName().equals(companyName))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getEmployeesByCompanyId(Long companyId) {
        List<Employee> employeesInCompany = employees.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
        return employeesInCompany.stream()
                .map(employee -> {
                    Map<String, Object> employeeMap = new HashMap<>();
                    employeeMap.put("id", employee.getId());
                    employeeMap.put("name", employee.getName());
                    employeeMap.put("age", employee.getAge());
                    employeeMap.put("gender", employee.getGender());
                    employeeMap.put("salary", employee.getSalary());
                    return employeeMap;
                })
                .collect(Collectors.toList());
    }

    public List<Company> listByCompanyPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
