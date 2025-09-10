package org.example.demo.service;

import org.example.demo.model.Employee;
import org.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void should_throw_extension_when_create_given_employee_with_age_17() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setAge(17);
        employee.setGender("Male");
        employee.setSalary(50000.0);
        assertThrows(EmployeeNotAmongLegalAgeException.class, () -> employeeService.createEmployee(employee));
        verify(employeeRepository, never()).save(employee);
    }

    @Test
    void should_throw_extension_when_create_given_employee_with_age_66(){
        Employee employee = new Employee();
        employee.setName("John");
        employee.setAge(66);
        employee.setGender("Male");
        employee.setSalary(50000.0);
        assertThrows(EmployeeNotAmongLegalAgeException.class, () -> employeeService.createEmployee(employee));
        verify(employeeRepository, never()).save(employee);
    }

    @Test
    void should_return_employee_when_create_given_employee_with_valid_age() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setAge(30);
        employee.setGender("Male");
        employee.setSalary(50000.0);
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee createdEmployee = employeeService.createEmployee(employee);
        assertEquals(employee, createdEmployee);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void should_throw_exception_when_get_employee_given_non_existing_id() {
        when(employeeRepository.findById(anyLong())).thenReturn(null);
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployee(5);
        });
        assertEquals("Employee with id 5 not found", exception.getMessage());
        verify(employeeRepository, times(1)).findById(anyLong());
    }
}