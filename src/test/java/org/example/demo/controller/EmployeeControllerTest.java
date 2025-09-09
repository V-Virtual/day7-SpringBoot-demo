package org.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                     "salary": 8000
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
}