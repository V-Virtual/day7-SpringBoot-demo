package org.example.demo.repository.dao;

import org.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByGender(String gender);

}
