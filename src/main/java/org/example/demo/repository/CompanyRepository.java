package org.example.demo.repository;

import org.example.demo.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CompanyRepository {

    void setUp();

    Company save(Company company);

    Company findById(long id);

    List<Company> findAll();

    List<Company> findByPageAndSize(Integer page, Integer size);

    void updateName(Company company, String name);

    void deleteById(long id);
}
