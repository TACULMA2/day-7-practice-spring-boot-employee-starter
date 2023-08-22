package com.thoughtworks.springbootemployee.controller;

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
        return companyRepository.findById(companyId);
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
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.addCompany(company);
    }

    @DeleteMapping("/{companyId}")
    public String deleteCompany(@PathVariable Long companyId) {
        companyRepository.deleteCompany(companyId);
        return "Company deleted successfully.";
    }
}
