package com.thoughtworks.springbootemployee.serivice;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.exception.EmployeeActiveStatusException;
import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employeeRepository.listByPage(pageNumber, pageSize);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.updateEmployee(id, updatedEmployee);
    }

    public Employee deleteEmployee(Long id) {
        return employeeRepository.deleteEmployee(id);
    }
}


