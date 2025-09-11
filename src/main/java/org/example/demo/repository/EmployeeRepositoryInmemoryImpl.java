package org.example.demo.repository;

import org.example.demo.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeRepositoryInmemoryImpl implements EmployeeRepository {
    private long id = 0;
    private final List<Employee> employees = new ArrayList<>();

    public void setUp() {
        id = 0;
        employees.clear();
    }

    public Employee save(Employee employee) {
        employee.setId(++id);
        employee.setActiveStatus(true);
        employees.add(employee);
        return employee;
    }

    public Employee findById(long id) {
        return employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findAll() {
        return employees;
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(emp -> gender.equals(emp.getGender()))
                .collect(Collectors.toList());
    }

    public List<Employee> findByPageAndSize(Integer page, Integer size) {
        return employees.stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

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
    }

    public void deleteEmployee(Employee employee) {
        employee.setActiveStatus(false);
    }
}
