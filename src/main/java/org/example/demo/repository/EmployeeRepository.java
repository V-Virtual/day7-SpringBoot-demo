package org.example.demo.repository;

import org.example.demo.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private long id = 0;
    private final List<Employee> employees = new ArrayList<>();

    public void setUp(){
        id = 0;
        employees.clear();
    }

    public Employee save(Employee employee) {
        employee.setId(++id);
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

    public List<Employee> findByGender(String gender){
        return employees.stream()
                .filter(emp -> gender.equals(emp.getGender()))
                .collect(Collectors.toList());
    }

    public List<Employee> findByPageAndSize(Integer page, Integer size){
        return employees.subList(Math.min(page * size, employees.size()), Math.min(page * size + size, employees.size()));
    }

    public void updateName(Employee employee, String name) {
        if (employee != null) {
            employee.setName(name);
        }
    }

    public void updateAge(Employee employee, int age) {
        if (employee != null) {
            employee.setAge(age);
        }
    }

    public void updateGender(Employee employee, String gender){
        if (employee != null) {
            employee.setGender(gender);
        }
    }

    public void updateSalary(Employee employee, double salary) {
        if (employee != null) {
            employee.setSalary(salary);
        }
    }

    public void deleteById(long id) {
        employees.removeIf(emp -> emp.getId() == id);
    }
}
