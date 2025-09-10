package org.example.demo.service;

import org.example.demo.model.Company;
import org.example.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getCompanies(Integer page, Integer size){
        if (page != null && size != null) {
            return companyRepository.findByPageAndSize(page, size);
        }
        return companyRepository.findAll();
    }

    public Company getCompany(long id) {
        return companyRepository.findById(id);
    }

    public Company updateCompany(long id, Map<String, Object> updates) {
        Company company = getCompany(id);
        companyRepository.updateName(company, (String) updates.get("name"));
        return company;
    }

    public void deleteCompany(long id) {
        companyRepository.deleteById(id);
    }
}
