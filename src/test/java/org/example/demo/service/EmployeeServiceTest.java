package org.example.demo.service;

import org.example.demo.model.Employee;
import org.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void should_return_extension_when_create_given_employee_with_age_17() {
        Employee employee = new Employee();
        employee.setAge(17);
        assertThrows(EmployeeNotAmongLegalAgeException.class, () -> employeeService.createEmployee(employee));
    }

    @Test
    void should_return_extension_when_create_given_employee_with_age_66(){
        Employee employee = new Employee();
        employee.setAge(66);
        assertThrows(EmployeeNotAmongLegalAgeException.class, () -> employeeService.createEmployee(employee));
    }
}