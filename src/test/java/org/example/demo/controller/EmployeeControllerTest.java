package org.example.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeController.getEmployees().clear();
    }

    @Test
    void should_return_employee_name_when_update_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void should_return_employee_when_get_employee_given_employee_id() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1));
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2));
        mockMvc.perform(get("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(32))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(5000.0));
    }

    @Test
    void should_return_employee_list_when_query_employee_given_employee_gender() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1));
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2));
        String requestBody3 = """
                {
                     "name": "Lucy",
                     "age": 25,
                     "gender": "Female",
                     "salary": 10000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody3));
        mockMvc.perform(get("/employees?gender=Female")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Lily"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].salary").value(8000.0))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[1].name").value("Lucy"))
                .andExpect(jsonPath("$[1].age").value(25))
                .andExpect(jsonPath("$[1].gender").value("Female"))
                .andExpect(jsonPath("$[1].salary").value(10000.0));
    }

    @Test
    void should_return_employee_list_when_get_all_employees() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1));
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2));
        mockMvc.perform(get("/employees-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Smith"))
                .andExpect(jsonPath("$[0].age").value(32))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(5000.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Lily"))
                .andExpect(jsonPath("$[1].age").value(20))
                .andExpect(jsonPath("$[1].gender").value("Female"))
                .andExpect(jsonPath("$[1].salary").value(8000.0));
    }

    @Test
    void should_return_employee_when_update_employee_given_a_valid_body() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1));
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lily"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(8000.0));
    }

    @Test
    void should_return_no_content_when_delete_employee_given_employee_id() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 5000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1));
        mockMvc.perform(delete("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}