package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Straw Hat"));
        companies.add(new Company(2L, "Heart Pirates"));
        companies.add(new Company(3L, "Kid Pirates"));
    }

    public List<Company> listAllCompanies(){
        return companies;
    }

    public Company findById(Long companyId) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }
}
