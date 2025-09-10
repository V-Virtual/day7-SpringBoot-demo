package org.example.demo.service;

import org.example.demo.model.Employee;
import org.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getEmployees(String gender, Integer page, Integer size){
        if (gender != null) {
            return employeeRepository.findByGender(gender);
        }
        if (page != null && size != null) {
            return employeeRepository.findByPageAndSize(page, size);
        }
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(long id, Map<String, Object> updates) {
        Employee employee = getEmployee(id);
        employeeRepository.updateName(employee, (String) updates.get("name"));
        employeeRepository.updateAge(employee, (Integer) updates.get("age"));
        employeeRepository.updateGender(employee, (String) updates.get("gender"));
        employeeRepository.updateSalary(employee, (Double) updates.get("salary"));
        return employee;
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
