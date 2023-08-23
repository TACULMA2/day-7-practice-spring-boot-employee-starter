package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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
        Company oocl = companyRepository.addCompany(new Company(1l,"OOCL"));
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
}
