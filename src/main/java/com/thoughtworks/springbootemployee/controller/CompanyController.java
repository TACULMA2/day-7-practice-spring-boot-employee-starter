package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.serivice.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/companies")
@RestController
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> listAllCompanies() {
        return companyService.listAllCompanies();
    }

    @GetMapping(path = "/{companyId}")
    public Company findByCompanyId(@PathVariable Long companyId) {
        return companyService.findByCompanyId(companyId);
    }

    @GetMapping(params = {"companyName"})
    public List<Company> findByCompanyName(@RequestParam String companyName) {
        return companyService.findByCompanyName(companyName);
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> listEmployeesByCompany(@PathVariable Long companyId) {
        return companyService.getEmployeesByCompanyId(companyId);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> findCompanyByPage(@RequestParam Long pageNumber, Long pageSize) {
        return companyService.listByCompanyPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping(path = "/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(companyId, updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Company deleteCompany(@PathVariable Long companyId) {
        return companyService.deleteCompany(companyId);
    }
}
