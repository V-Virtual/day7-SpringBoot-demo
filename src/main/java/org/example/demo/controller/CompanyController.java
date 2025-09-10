package org.example.demo.controller;

import org.example.demo.model.Company;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private long id = 0;
    private final List<Company> companies = new ArrayList<>();

    @PostMapping
    @ResponseStatus(CREATED)
    public Company createCompanies(@RequestBody Company company) {
        company.setId(++id);
        companies.add(company);
        return company;
    }

    @GetMapping
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

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable long id) {
        return companies.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable long id, @RequestBody Map<String, Object> updates){
        Company company = getCompany(id);
        if (company != null) {
            if (updates.containsKey("name")) {
                company.setName((String) updates.get("name"));
            }
        }
        return company;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable long id) {
        companies.removeIf(emp -> emp.getId() == id);
    }

    public void setUp(){
        id = 0;
        companies.clear();
    }
}
