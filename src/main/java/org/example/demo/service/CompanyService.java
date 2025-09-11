package org.example.demo.service;

import org.example.demo.model.Company;
import org.example.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getCompanies(Integer page, Integer size) {
        if (page != null && size != null) {
            return companyRepository.findByPageAndSize(page, size);
        }
        return companyRepository.findAll();
    }

    public Company getCompany(long id) {
        Company company = companyRepository.findById(id);
        if (company == null) {
            throw new CompanyNotFoundException("Company with id " + id + " not found");
        }
        return company;
    }

    public Company updateCompany(long id, Company updateCompany) {
        Company company = getCompany(id);
        companyRepository.updateCompany(company, updateCompany);
        return company;
    }

    public void deleteCompany(long id) {
        companyRepository.deleteById(id);
    }
}
