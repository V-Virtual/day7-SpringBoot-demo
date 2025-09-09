package org.example.demo.controller;

import org.example.demo.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    @PostMapping("/employees")
    @ResponseStatus(CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return employee;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable long id) {
        return employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/employees")
    public List<Employee> queryEmployeeByGender(@RequestParam String gender) {
        List<Employee> result = employees.stream()
                .filter(emp -> gender.equals(emp.getGender()))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/employees-all")
    public List<Employee> getAllEmployees() {
        return employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
