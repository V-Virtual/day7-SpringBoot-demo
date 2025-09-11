package org.example.demo.repository;

import org.example.demo.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository {

    default void setUp() {

    }

    Employee save(Employee employee);

    Employee findById(long id);

    List<Employee> findAll();

    List<Employee> findByGender(String gender);

    List<Employee> findByPageAndSize(Integer page, Integer size);

    void updateEmployee(Employee employee, Employee updatedEmployee);

    void deleteEmployee(Employee employee);
}
