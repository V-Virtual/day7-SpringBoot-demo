package org.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getGender() == gender) {
                result.add(emp);
            }
        }
        return result;
    }

    static class Employee {
        private long id;
        private String name;
        private int age;
        private double salary;
        private String gender;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
