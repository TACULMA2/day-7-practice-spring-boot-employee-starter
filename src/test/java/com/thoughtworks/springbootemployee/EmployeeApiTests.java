package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mOckMvcClient;
    @BeforeEach
    void cleanupEmployeeData(){
        employeeRepository.cleanALl();
    }

    @Test
    void should_return_all_given_employees_when_perform_get_employees() throws Exception {
        //given
        Employee alice = employeeRepository.addEmployee(new Employee("Alice", 24, "Female", 9000));
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("[0].salary").value(alice.getSalary())); //JSON format data //$ means it will the first file
        //then
    }

    @Test
    void should_return_the_employee_when_perform_get_employee_given_a_employee_id() throws Exception {
    //given
     Employee alice = employeeRepository.addEmployee(new Employee("Alice", 24, "Female", 9000));
     employeeRepository.addEmployee(new Employee("Bob", 28, "Male", 8000));
     //when
     mOckMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alice.getId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()));
     //then
    }
    
    @Test
    void should_should_return_404_not_found_when_perform_get_employee_given_a_not_existed_id() throws Exception {
    //given
     long notExistedEmployeeId=99L;
     //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistedEmployeeId))
                .andExpect(status().isNotFound());
     //then
    }

//    @Test
//    void should__when__given_() {
//    //given
//
//     //when
//
//     //then
//    }
}