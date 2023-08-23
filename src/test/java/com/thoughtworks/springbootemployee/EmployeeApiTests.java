package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    @Test
    void should_return_no_content_from_same_employee_number_when_perform_delete_given_a_employee_number() throws Exception{
        //given
        Employee deleteEmployee = employeeRepository.addEmployee(new Employee(1L,"Alice", 24, "Female", 9000));
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.delete("/employees/"+ deleteEmployee.getId()))
                .andExpect(status().isNoContent());
        //then
        Assertions.assertTrue(employeeRepository.listAll().isEmpty());
    }
    
    @Test
    void should_return_list_of_employees_with_given_range_when_get_employees_given_pageNUmber_and_pageSize() throws Exception {
    //given
        Employee firstEmployee = employeeRepository.addEmployee(new Employee(1L, "Monkey D. Luffy", 19, "Male", 300000, 1L));
        Employee secondEmployee = employeeRepository.addEmployee(new Employee(2L, "Ronoroa Zoro", 21, "Male", 111100, 1L));
        Employee thirdEmployee = employeeRepository.addEmployee(new Employee(3L, "Nami", 20, "Female", 36600, 1L));
        Long pageNumber = 1L;
        Long pageSize = 2L;
     //when
        String queryString = String.format("/employees?pageNumber=%d&pageSize=%d", pageNumber, pageSize);
        mOckMvcClient.perform(MockMvcRequestBuilders.get(queryString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(firstEmployee.getId()))
                .andExpect(jsonPath("$[0].name").value(firstEmployee.getName()))
                .andExpect(jsonPath("$[0].age").value(firstEmployee.getAge()))
                .andExpect(jsonPath("$[0].gender").value(firstEmployee.getGender()))
                .andExpect(jsonPath("[0].salary").value(firstEmployee.getSalary()))
                .andExpect(jsonPath("$[1].id").value(secondEmployee.getId()))
                .andExpect(jsonPath("$[1].name").value(secondEmployee.getName()))
                .andExpect(jsonPath("$[1].age").value(secondEmployee.getAge()))
                .andExpect(jsonPath("$[1].gender").value(secondEmployee.getGender()))
                .andExpect(jsonPath("$[1].salary").value(secondEmployee.getSalary()));
     //then
    }
}
