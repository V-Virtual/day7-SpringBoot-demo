package org.example.demo.repository;

import org.example.demo.model.Company;
import org.example.demo.repository.dao.CompanyJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyReposityDBImpl implements CompanyRepository {

    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    @Override
    public void setUp() {
        companyJpaRepository.deleteAll();
    }

    @Override
    public Company save(Company company) {
        companyJpaRepository.save(company);
        return company;
    }

    @Override
    public Company findById(long id) {
        return companyJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> findAll() {
        return companyJpaRepository.findAll();
    }

    @Override
    public List<Company> findByPageAndSize(Integer page, Integer size) {
        return companyJpaRepository.findAll().stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCompany(Company company, Company updateCompany) {
        if (updateCompany.getName() != null) {
            company.setName(updateCompany.getName());
        }
        companyJpaRepository.save(company);
    }

    public void deleteById(long id) {
        companyJpaRepository.deleteById(id);
    }
}
