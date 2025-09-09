package org.example.demo.controller;

import org.example.demo.model.Company;
import org.example.demo.model.Employee;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class CompanyController {

    private final List<Company> companies = new ArrayList<>();

    @PostMapping("/companies")
    @ResponseStatus(CREATED)
    public Company createCompanies(@RequestBody Company company) {
        company.setId(companies.size() + 1);
        companies.add(company);
        return company;
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
