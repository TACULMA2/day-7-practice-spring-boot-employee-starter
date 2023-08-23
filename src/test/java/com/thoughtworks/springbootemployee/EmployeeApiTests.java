package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
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
    void cleanupEmployeeData() {
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
        long notExistedEmployeeId = 99L;
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistedEmployeeId))
                .andExpect(status().isNotFound());
        //then
    }

    @Test
    void should_should_return_employees_by_given_gender_when_perform_get_employees_given_a_gender() throws Exception {
        //given
        Employee alice = employeeRepository.addEmployee(new Employee("Alice", 24, "Female", 9000));
        Employee bob = employeeRepository.addEmployee(new Employee("Bob", 28, "Male", 8000));
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/employees/").param("gender", "Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("[0].salary").value(alice.getSalary()));
        //then
    }

    @Test
    void should_return_the_employee_when_perform_post_employees_given_a_new_employee_with_JSON_format() throws Exception {
        //given
        Employee newEmployee = new Employee("Alice", 24, "Female", 9000);
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.post("/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(newEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(newEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()));
        //then
    }
    
    @Test
    void should_return_updated_employee_when_perform_put_employees_given_a_employee_id() throws Exception {
    //given
        Employee newEmployee = employeeRepository.addEmployee(new Employee("Alice", 24, "Female", 9000));
        Employee updatedEmployee = new Employee("Alice", 22, "Female", 100000);
     //when
        mOckMvcClient.perform(MockMvcRequestBuilders.put("/employees/"+ newEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newEmployee.getId()))
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(updatedEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(updatedEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()));
     //then
    }
}
