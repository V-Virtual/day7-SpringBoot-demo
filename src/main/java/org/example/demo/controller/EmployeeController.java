package org.example.demo.controller;

import org.example.demo.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("employees")
@RestController
public class EmployeeController {

    private int id = 0;
    private final List<Employee> employees = new ArrayList<>();

    @PostMapping
    @ResponseStatus(CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        employee.setId(++id);
        employees.add(employee);
        return employee;
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable long id) {
        return employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<Employee> result = employees;
        if (gender != null) {
            result = result.stream()
                    .filter(emp -> gender.equals(emp.getGender()))
                    .collect(Collectors.toList());
        }
        if (page != null && size != null) {
            int fromIndex = Math.min(page * size, result.size());
            int toIndex = Math.min(fromIndex + size, result.size());
            result = result.subList(fromIndex, toIndex);
        }
        return result;
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        Employee employee = getEmployee(id);
        if (employee != null) {
            if (updates.containsKey("name")) {//refactor
                employee.setName((String) updates.get("name"));
            }
            if (updates.containsKey("age")) {
                employee.setAge((Integer) updates.get("age"));
            }
            if (updates.containsKey("gender")) {
                employee.setGender((String) updates.get("gender"));
            }
            if (updates.containsKey("salary")) {
                employee.setSalary((Double) updates.get("salary"));
            }
        }
        return employee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable long id) {
        employees.removeIf(emp -> emp.getId() == id);
    }

    public void setUp(){
        id = 0;
        employees.clear();
    }
}
