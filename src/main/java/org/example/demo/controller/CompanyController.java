package org.example.demo.controller;

import org.example.demo.model.Company;
import org.example.demo.model.CompanyRequest;
import org.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Company createCompanies(@RequestBody CompanyRequest companyRequest) {
        Company company = new Company(companyRequest);
        return companyService.createCompany(company);
    }

    @GetMapping
    public List<Company> getCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return companyService.getCompanies(page, size);
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable long id) {
        return companyService.getCompany(id);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable long id, @RequestBody CompanyRequest updateCompanyRequest) {
        Company updateCompany = new Company(updateCompanyRequest);
        return companyService.updateCompany(id, updateCompany);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
    }
}
