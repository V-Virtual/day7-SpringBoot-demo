package org.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.setUp();
    }

    private long createEmployee(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform((post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }

    @Test
    void should_return_employee_when_create_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 50000.0
                }
                """;
        long id = createEmployee(requestBody);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id + 1));
    }

    @Test
    void should_return_employee_when_get_employee_given_employee_id() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 50000.0
                }
                """;
        long id = createEmployee(requestBody1);
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
        mockMvc.perform(get("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(32))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(50000.0));
    }

    @Test
    void should_return_employee_list_when_query_employee_given_employee_gender() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 50000.0
                }
                """;
        long id1 = createEmployee(requestBody1);
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        long id2 = createEmployee(requestBody2);
        String requestBody3 = """
                {
                     "name": "Lucy",
                     "age": 25,
                     "gender": "Female",
                     "salary": 10000.0
                }
                """;
        long id3 = createEmployee(requestBody3);
        mockMvc.perform(get("/employees?gender=Female")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(id2))
                .andExpect(jsonPath("$[0].name").value("Lily"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].salary").value(8000.0))
                .andExpect(jsonPath("$[1].id").value(id3))
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
                    "salary": 50000.0
                }
                """;
        long id1 = createEmployee(requestBody1);
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        long id2 = createEmployee(requestBody2);
        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value("John Smith"))
                .andExpect(jsonPath("$[0].age").value(32))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(50000.0))
                .andExpect(jsonPath("$[1].id").value(id2))
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
                    "salary": 50000.0
                }
                """;
        long id = createEmployee(requestBody1);
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        mockMvc.perform(put("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Lily"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(8000.0));
    }

    @Test
    void should_return_no_content_when_delete_employee_given_employee_id() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 50000.0
                }
                """;
        long id = createEmployee(requestBody);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        mockMvc.perform(delete("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(32))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(50000.0))
                .andExpect(jsonPath("$.activeStatus").value(false));
    }

    @Test
    void should_return_employee_list_when_get_employees_given_page_and_size() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 50000.0
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
        String requestBody4 = """
                {
                    "name": "Frank",
                    "age": 32,
                    "gender": "Male",
                    "salary": 50000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody4));
        String requestBody5 = """
                {
                     "name": "Jake",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody5));
        String requestBody6 = """
                {
                     "name": "Paul",
                     "age": 25,
                     "gender": "Female",
                     "salary": 10000.0
                }
                """;
        long id = createEmployee(requestBody6);
        mockMvc.perform(get("/employees?page=1&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value("Paul"))
                .andExpect(jsonPath("$[0].age").value(25))
                .andExpect(jsonPath("$[0].gender").value("Female"))
                .andExpect(jsonPath("$[0].salary").value(10000.0));
    }

    @Test
    void should_return_no_content_when_get_employee_given_non_existing_employee_id() throws Exception {
        mockMvc.perform(get("/employees/{id}", 99)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with id 99 not found"));
    }

    @Test
    void should_throw_exception_when_create_employee_given_employee_with_age_17() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 17,
                    "gender": "Male",
                    "salary": 18000.0
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Employee age must be between 18 and 65"));
    }

    @Test
    void should_throw_exception_when_create_employee_given_employee_with_age_66() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 66,
                    "gender": "Male",
                    "salary": 18000.0
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Employee age must be between 18 and 65"));
    }

    @Test
    void should_throw_exception_when_create_given_employee_with_age_above_30_and_salary_below_20000() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 18000.0
                }
                """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Employee salary cannot be less than 20000 for age over 30"));
    }

    @Test
    void should_throw_exception_when_delete_employee_given_non_existing_employee_id() throws Exception {
        mockMvc.perform(delete("/employees/{id}", 99)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with id 99 not found"));
    }

    @Test
    void should_throw_exception_when_update_employee_given_non_existing_employee_id() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 32,
                    "gender": "Male",
                    "salary": 50000.0
                }
                """;
        long id = createEmployee(requestBody);
        mockMvc.perform(delete("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
        String requestBody2 = """
                {
                     "name": "Lily",
                     "age": 20,
                     "gender": "Female",
                     "salary": 8000.0
                }
                """;
        mockMvc.perform(put("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Update failed, the Employee has already left the company"));
    }
}