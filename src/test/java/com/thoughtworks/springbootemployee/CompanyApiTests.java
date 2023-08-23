package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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
    private MockMvc mOckMvcClient;

    @BeforeEach
    void cleanupCompanyData() {
        companyRepository.cleanAll();
    }

    @Test
    void should_return_all_given_companies_when_perform_get_companies() throws Exception {
        //given
        Company oocl = companyRepository.addCompany(new Company("OOCL"));
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyId").value(oocl.getCompanyId()))
                .andExpect(jsonPath("$[0].companyName").value(oocl.getCompanyName()));
    }

    @Test
    void should_return_the_company_when_perform_get_company_given_a_company_id() throws Exception {
        //given
        Company oocl = companyRepository.addCompany(new Company(1l, "OOCL"));
        companyRepository.addCompany(new Company(2l, "COSCO"));
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + oocl.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(oocl.getCompanyId()))
                .andExpect(jsonPath("$.companyName").value(oocl.getCompanyName()));
        //then
    }

    @Test
    void should_should_return_404_not_found_when_perform_get_company_given_a_not_existed_company_id() throws Exception {
        //given
        long notExistedCompanyId = 99L;
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + notExistedCompanyId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_should_return_companies_by_given_name_when_perform_get_companies_given_a_name() throws Exception {
        //given
        Company oocl = companyRepository.addCompany(new Company(1l, "OOCL"));
        Company cosco = companyRepository.addCompany(new Company(2l, "COSCO"));
        //when
        mOckMvcClient.perform(MockMvcRequestBuilders.get("/companies/").param("companyName", "OOCL"))
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
        mOckMvcClient.perform(MockMvcRequestBuilders.post("/companies/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").value(notNullValue()))
                .andExpect(jsonPath("$.companyName").value(newCompany.getCompanyName()));
    }
}
