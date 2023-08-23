package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.exception.EmployeeActiveStatusException;
import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.serivice.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;
    @BeforeEach
    void setUp() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }
    @Test
    void should_return_created_employee_when_create_given_employee_service_with_valid_age() {
    //given
        Employee employee = new Employee(null, "Lucy", 20 , "Female", 3000);
        Employee savedEmployee = new Employee(1L, "Lucy", 20 , "Female", 3000);
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
     //when

        Employee employeeResponse = employeeService.create(employee);
     //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employeeResponse.getName());
        assertEquals(20, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(3000, employeeResponse.getSalary());
    }

    @Test
    void should_throw_exception_when_create_given_service_and_employee_whose_is_less_than_18() {
    //given
        Employee employee = new Employee(null, "Lucy", 17 , "Female", 3000);
     //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
     //then
        assertEquals("Employees must be 18-65 years old.", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_service_and_employee_whose_is_more_than_65() {
    //given
        Employee employee = new Employee(null, "Lucy", 66 , "Female", 3000);
     //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
     //then
        assertEquals("Employees must be 18-65 years old.", employeeCreateException.getMessage());
    }

    @Test
    void should_return_employee_active_status_false_when_delete_given_employee() {
    //given
        Employee employee = new Employee(null, "Lucy", 30 , "Female", 3000);
     //when
        employee.setStatus(Boolean.TRUE);
        when(mockedEmployeeRepository.findById((employee.getId()))).thenReturn(employee);
         employeeService.delete(employee.getId());
     //then
        verify(mockedEmployeeRepository).updateEmployee(eq(employee.getId()), argThat(tempEmployee -> {
            assertFalse(tempEmployee.isStatus());
            assertEquals("Lucy", tempEmployee.getName());
            assertEquals(30, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(3000, tempEmployee.getSalary());
            return true;
        }));
    }

    @Test
    void should_throw_exception_when_update_given_employee_status_inactive() {
        // Given
        Employee employee = new Employee(1L, "Lucy", 30 , "Female", 3000);
        employee.setStatus(false);
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);

        // When and Then
        EmployeeActiveStatusException employeeActiveStatusException = assertThrows(EmployeeActiveStatusException.class, () -> {
            employeeService.update(employee);
        });

        // Then
        assertEquals("Employee is inactive.", employeeActiveStatusException.getMessage());
    }
}
