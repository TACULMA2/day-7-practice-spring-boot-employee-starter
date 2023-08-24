package com.thoughtworks.springbootemployee.serivice;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService {
    public final CompanyRepository companyRepository;
    public final EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    public Company findByCompanyId(Long companyId) {
        return companyRepository.findByCompanyId(companyId);
    }

    public List<Company> findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    public List<Employee> getEmployeesByCompanyId(Long companyId) {
        return employeeRepository.getEmployeesByCompanyId(companyId);
    }


    public List<Company> listByCompanyPage(Long pageNumber, Long pageSize) {
        return companyRepository.listByCompanyPage(pageNumber, pageSize);
    }

    public Company addCompany(Company company) {
        return companyRepository.addCompany(company);
    }

    public Company updateCompany(Long companyId, Company updatedCompany) {
        return companyRepository.updateCompany(companyId, updatedCompany);
    }

    public Company deleteCompany(Long companyId) {
        return companyRepository.deleteCompany(companyId);
    }
}
