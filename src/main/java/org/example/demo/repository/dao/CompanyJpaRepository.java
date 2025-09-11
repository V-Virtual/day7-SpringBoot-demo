package org.example.demo.repository.dao;

import org.example.demo.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {

}
