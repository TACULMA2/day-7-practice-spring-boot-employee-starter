package com.thoughtworks.springbootemployee.serivice;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.exception.EmployeeActiveStatusException;
import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    public final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.hasInvalidAge()) {
            throw new EmployeeCreateException();
        }
        return employeeRepository.addEmployee((employee));
    }

    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findById(id);
        matchedEmployee.setStatus(Boolean.FALSE);
        employeeRepository.updateEmployee(id, matchedEmployee);
    }

    public void update(Employee employee){
        if (!employee.isStatus()) {
            throw new EmployeeActiveStatusException();
        }
        Employee matchedEmployee = employeeRepository.findById(employee.getId());
        matchedEmployee.merge(employee);
        employeeRepository.updateEmployee(employee.getId(), matchedEmployee);
    }
}


