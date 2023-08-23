package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private Long companyId;
    private String companyName;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company(){
    }

    public Company(String companyName){
        this.companyName = companyName;
    }
    public Company(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public void updateFromCompany(Company company) {
        this.companyId = company.companyId;
        this.companyName = company.companyName;
    }

    private List<Employee> employees = new ArrayList<>(); // Initialize the list

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
