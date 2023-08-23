package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class CompanyApiTests {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanupCompanyData() {
        companyRepository.cleanAll();
        employeeRepository.cleanALl();
    }

    @Test
    void should_return_all_given_companies_when_perform_get_companies() throws Exception {
        //given
        Company oocl = companyRepository.addCompany(new Company("OOCL"));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyId").value(oocl.getCompanyId()))
                .andExpect(jsonPath("$[0].companyName").value(oocl.getCompanyName()));
    }

    @Test
    void should_return_the_company_when_perform_get_company_given_a_company_id() throws Exception {
        //given
        Company oocl = companyRepository.addCompany(new Company(1L, "OOCL"));
        companyRepository.addCompany(new Company(2L, "COSCO"));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + oocl.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(oocl.getCompanyId()))
                .andExpect(jsonPath("$.companyName").value(oocl.getCompanyName()));
    }

    @Test
    void should_should_return_404_not_found_when_perform_get_company_given_a_not_existed_company_id() throws Exception {
        //given
        long notExistedCompanyId = 99L;
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + notExistedCompanyId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_should_return_companies_by_given_name_when_perform_get_companies_given_a_name() throws Exception {
        //given
        Company oocl = companyRepository.addCompany(new Company(1L, "OOCL"));
        companyRepository.addCompany(new Company(2L, "COSCO"));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/").param("companyName", "OOCL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyId").value(oocl.getCompanyId()))
                .andExpect(jsonPath("$[0].companyName").value(oocl.getCompanyName()));
    }

    @Test
    void should_return_the_companies_when_perform_post_companies_given_a_new_company_with_JSON_format() throws Exception {
        //given
        Company newCompany = new Company("COSCO");
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.post("/companies/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").value(notNullValue()))
                .andExpect(jsonPath("$.companyName").value(newCompany.getCompanyName()));
    }

    @Test
    void should_return_updated_company_when_perform_put_companies_given_a_company_id() throws Exception {
        //given
        Company newCompany = companyRepository.addCompany(new Company("COSCO"));
        Company updatedCompany = new Company("OOCl");
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.put("/companies/" + newCompany.getCompanyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(newCompany.getCompanyId()))
                .andExpect(jsonPath("$.companyName").value(updatedCompany.getCompanyName()));
    }

    @Test
    void should_return_no_content_from_same_company_id_when_perform_delete_given_a_company_id() throws Exception {
        //given
        Company deleteCompany = companyRepository.addCompany(new Company(1L, "OOCL"));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/companies/" + deleteCompany.getCompanyId()))
                .andExpect(status().isNoContent());
        //then
        Assertions.assertTrue(companyRepository.listAllCompanies().isEmpty());
    }

    @Test
    void should_return_list_of_companies_with_given_range_when_get_companies_given_pageNumber_and_pageSize() throws Exception {
        //given
        Company oocl = companyRepository.addCompany(new Company(1L, "OOCL"));
        Company cosco = companyRepository.addCompany(new Company(2L, "COSCO"));
        Company tw = companyRepository.addCompany(new Company(3L, "TW"));
        Company scape = companyRepository.addCompany(new Company(4L, "SCAPE"));
        companyRepository.addCompany(new Company(5L, "DD"));
        Long pageNumber = 1L;
        Long pageSize = 4L;
        //when
        String queryString = String.format("/companies?pageNumber=%d&pageSize=%d", pageNumber, pageSize);
        mockMvcClient.perform(MockMvcRequestBuilders.get(queryString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].companyId").value(oocl.getCompanyId()))
                .andExpect(jsonPath("$[0].companyName").value(oocl.getCompanyName()))
                .andExpect(jsonPath("$[1].companyId").value(cosco.getCompanyId()))
                .andExpect(jsonPath("$[1].companyName").value(cosco.getCompanyName()))
                .andExpect(jsonPath("$[2].companyId").value(tw.getCompanyId()))
                .andExpect(jsonPath("$[2].companyName").value(tw.getCompanyName()))
                .andExpect(jsonPath("$[3].companyId").value(scape.getCompanyId()))
                .andExpect(jsonPath("$[3].companyName").value(scape.getCompanyName()));
    }

    @Test
    void should_return_employees_when_performs_get_given_company_id() throws Exception {
        Company oocl = companyRepository.addCompany(new Company(1L, "OOCL"));
        Employee alice = employeeRepository.addEmployee(new Employee(1L, "Alice", 24, "Female", 9000, oocl.getCompanyId()));
        String queryString = String.format("/companies/%d/employees", oocl.getCompanyId());
        mockMvcClient.perform(MockMvcRequestBuilders.get(queryString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(alice.getId()))  // Access the first element
                .andExpect(jsonPath("[0].name").value(alice.getName()))
                .andExpect(jsonPath("[0].age").value(alice.getAge()))
                .andExpect(jsonPath("[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("[0].salary").value(alice.getSalary()));
    }
}
