package org.example.demo.service;

import org.example.demo.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private long id = 0;
    private final List<Employee> employees = new ArrayList<>();

    public void setUp(){
        id = 0;
        employees.clear();
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(++id);
        employees.add(employee);
        return employee;
    }

    public Employee getEmployee(long id) {
        return employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployees(String gender, Integer page, Integer size){
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

    public Employee updateEmployee(long id, Map<String, Object> updates) {
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

    public void deleteEmployee(long id) {
        employees.removeIf(emp -> emp.getId() == id);
    }
}
