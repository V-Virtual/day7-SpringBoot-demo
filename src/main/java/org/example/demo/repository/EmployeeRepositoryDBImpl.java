package org.example.demo.repository;

import org.example.demo.model.Employee;
import org.example.demo.repository.dao.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryDBImpl implements EmployeeRepository {

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Override
    public void setUp() {
        employeeJpaRepository.deleteAll();
    }

    @Override
    public Employee save(Employee employee) {
        employee.setActive(true);
        employeeJpaRepository.save(employee);
        return employee;
    }

    @Override
    public Employee findById(long id) {
        return employeeJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public List<Employee> findByGender(String gender) {
        return employeeJpaRepository.findByGender(gender);
    }

    @Override
    public List<Employee> findByPageAndSize(Integer page, Integer size) {
        return employeeJpaRepository.findAll().stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEmployee(Employee employee, Employee updatedEmployee) {
        if (!updatedEmployee.getName().isEmpty()) {
            employee.setName(updatedEmployee.getName());
        }
        if (updatedEmployee.getAge() != 0) {
            employee.setAge(updatedEmployee.getAge());
        }
        if (!updatedEmployee.getGender().isEmpty()) {
            employee.setGender(updatedEmployee.getGender());
        }
        if (updatedEmployee.getSalary() != 0) {
            employee.setSalary(updatedEmployee.getSalary());
        }
        employeeJpaRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employee.setActive(false);
        employeeJpaRepository.save(employee);
    }
}
