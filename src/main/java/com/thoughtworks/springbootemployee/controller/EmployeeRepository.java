package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();
    public static final long START_ID_MINUS_ONE = 0L;
    public static final int ID_INCREMENT = 1;

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

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Long id = generateNextId();
        Employee toBeSavedEmployee = new Employee(id, employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary());
        employees.add(toBeSavedEmployee);
        return toBeSavedEmployee;
    }

    private Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber-1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void updateEmployee(Long id, Employee updatedEmployee) {
        Employee employeeToUpdate = employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        employeeToUpdate.setAge(updatedEmployee.getAge());
        employeeToUpdate.setSalary(updatedEmployee.getSalary());
    }
}
