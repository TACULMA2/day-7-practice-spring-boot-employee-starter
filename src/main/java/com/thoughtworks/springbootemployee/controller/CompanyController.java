package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/companies")
@RestController
public class CompanyController {
    private final CompanyRepository companyRepository;
    @Autowired
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
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.addCompany(company);
    }

    @PutMapping(path = "/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany) {
        return companyRepository.updateCompany(companyId, updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Company deleteCompany(@PathVariable Long companyId) {
        return companyRepository.deleteCompany(companyId);
    }
}
