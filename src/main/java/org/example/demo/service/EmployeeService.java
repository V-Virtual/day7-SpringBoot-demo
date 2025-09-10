package org.example.demo.service;

import org.example.demo.model.Employee;
import org.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new EmployeeNotAmongLegalException("Employee age must be between 18 and 65");
        }
        if(employee.getAge() > 30 && employee.getSalary() < 20000){
            throw new EmployeeNotAmongLegalException("Employee salary cannot be less than 20000 for age over 30");
        }
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        return employee;
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
        Employee employee = getEmployee(id);
        if(employee == null){
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        employeeRepository.deleteEmployee(employee);
    }
}
