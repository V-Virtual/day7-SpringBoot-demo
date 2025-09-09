package org.example.demo.controller;

import org.example.demo.model.Company;
import org.example.demo.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/companies")
    public List<Company> getCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<Company> result = companies;
        if (page != null && size != null) {
            int fromIndex = Math.min(page * size, result.size());
            int toIndex = Math.min(fromIndex + size, result.size());
            result = result.subList(fromIndex, toIndex);
        }
        return result;
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
