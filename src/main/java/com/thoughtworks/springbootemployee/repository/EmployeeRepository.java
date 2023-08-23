package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.util.TestDataUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = TestDataUtil.createEmployees();
    public static final long START_ID_MINUS_ONE = 0L;
    public static final int ID_INCREMENT = 1;

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
        Employee toBeSavedEmployee = new Employee(id,
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary(),
                employee.getCompanyId());
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
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee employeeToUpdate = findById(id);
        employeeToUpdate.merge(employee);
        return employeeToUpdate;
    }

    public Employee deleteEmployee(Long id) {
        Employee employeeToDelete = findById(id);
        employees.remove(employeeToDelete);
        return employeeToDelete;
    }

    public List<Employee> getEmployeesByCompanyId(Long companyId) {
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    public void cleanALl() {
        employees.clear();
    }
}
