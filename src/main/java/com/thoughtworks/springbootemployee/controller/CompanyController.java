package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/companies")
@RestController
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    @GetMapping(path = "/{companyId}")
    public Company findByCompanyId(@PathVariable Long companyId) {
        return companyRepository.findByCompanyId(companyId);
    }

    @GetMapping(params = {"companyName"})
    public List<Company> findByCompanyName(@RequestParam String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Map<String, Object>> listEmployeesByCompany(@PathVariable Long companyId) {
        return companyRepository.getEmployeesByCompanyId(companyId);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> findCompanyByPage(@RequestParam Long pageNumber, Long pageSize) {
        return companyRepository.listByCompanyPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addCompany(@RequestBody Company company) {
        companyRepository.addCompany(company);
        return "Company added successfully.";
    }

    @PutMapping(path = "/{id}")
    public String updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany) {
        companyRepository.updateCompany(companyId, updatedCompany);
        return "Employee updated successfully.";
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCompany(@PathVariable Long companyId) {
        companyRepository.deleteCompany(companyId);
        return "Company deleted successfully.";
    }
}
