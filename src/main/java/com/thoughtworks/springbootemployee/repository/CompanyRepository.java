package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.util.TestDataUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    private static final List<Employee> employees = TestDataUtil.createEmployees();
    public static final long START_ID_MINUS_ONE = 0L;
    public static final int ID_INCREMENT = 1;

    static {
        companies.add(new Company(1L, "Straw Hat"));
        companies.add(new Company(2L, "Heart Pirates"));
        companies.add(new Company(3L, "Kid Pirates"));
    }

    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findByCompanyId(Long companyId) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Company> findByCompanyName(String companyName) {
        return companies.stream()
                .filter(employee -> employee.getCompanyName().equals(companyName))
                .collect(Collectors.toList());
    }

    public List<Company> listByCompanyPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        Long companyId = generateNextId();
        Company newCompany = new Company(companyId, company.getCompanyName());
        companies.add(newCompany);
        return newCompany;
    }

    private Long generateNextId() {
        return companies.stream()
                .mapToLong(Company::getCompanyId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public Company updateCompany(Long companyId, Company company) {
        Company companyToUpdate = findByCompanyId(companyId);
        companyToUpdate.setCompanyName(company.getCompanyName());
        return companyToUpdate;
    }

    public Company deleteCompany(Long companyId) {
        Company companyToDelete = findByCompanyId(companyId);
        companies.remove(companyToDelete);
        return companyToDelete;
    }

    public void cleanAll() {
        companies.clear();
    }
}
