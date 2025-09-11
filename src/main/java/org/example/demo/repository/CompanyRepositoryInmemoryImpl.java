package org.example.demo.repository;

import org.example.demo.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepositoryInmemoryImpl implements CompanyRepository {

    private long id = 0;
    private final List<Company> companies = new ArrayList<>();

    public void setUp() {
        id = 0;
        companies.clear();
    }

    public Company save(Company company) {
        company.setId(++id);
        companies.add(company);
        return company;
    }

    public Company findById(long id) {
        return companies.stream()
                .filter(comp -> comp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Company> findAll() {
        return companies;
    }

    public List<Company> findByPageAndSize(Integer page, Integer size) {
        return companies.subList(Math.min(page * size, companies.size()), Math.min(page * size + size, companies.size()));
    }

    public void updateName(Company company, String name) {
        if (company != null) {
            company.setName(name);
        }
    }

    public void deleteById(long id) {
        companies.removeIf(comp -> comp.getId() == id);
    }
}
