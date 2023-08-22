package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/employees")
@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    @GetMapping(path = "/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> findByPage(@RequestParam Long pageNumber, Long pageSize) {
        return employeeRepository.listByPage(pageNumber, pageSize);
    }

    @PutMapping(path = "/{id}")
    public String updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        employeeRepository.updateEmployee(id, updatedEmployee);
        return "Employee updated successfully.";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteEmployee(id);
        return "Employee deleted successfully.";
    }
}
