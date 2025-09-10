package org.example.demo.service;

import org.example.demo.model.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    private long id = 0;
    private final List<Company> companies = new ArrayList<>();

    public void setUp(){
        id = 0;
        companies.clear();
    }

    public Company createCompany(Company company) {
        company.setId(++id);
        companies.add(company);
        return company;
    }

    public List<Company> getCompanies(Integer page, Integer size){
        List<Company> result = companies;
        if (page != null && size != null) {
            int fromIndex = Math.min(page * size, result.size());
            int toIndex = Math.min(fromIndex + size, result.size());
            result = result.subList(fromIndex, toIndex);
        }
        return result;
    }

    public Company getCompany(long id) {
        return companies.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Company updateCompany(long id, java.util.Map<String, Object> updates) {
        Company company = getCompany(id);
        if (company != null) {
            if (updates.containsKey("name")) {
                company.setName((String) updates.get("name"));
            }
        }
        return company;
    }

    public void deleteCompany(long id) {
        companies.removeIf(emp -> emp.getId() == id);
    }
}
