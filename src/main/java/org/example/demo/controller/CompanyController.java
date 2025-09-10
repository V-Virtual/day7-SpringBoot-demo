package org.example.demo.controller;

import org.example.demo.model.Company;
import org.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Company createCompanies(@RequestBody Company company) {
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
    public Company updateCompany(@PathVariable long id, @RequestBody Map<String, Object> updates){
        return companyService.updateCompany(id, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
    }
}
