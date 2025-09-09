package org.example.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        companyController.getCompanies().clear();
    }

    @Test
    void should_return_company_when_create_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "google"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void should_return_company_list_when_getCompanies() throws Exception {
        String requestBody1 = """
                {
                    "name": "google"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1));

        String requestBody2 = """
                {
                    "name": "apple"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2));

        mockMvc.perform(get("/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("google"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("apple"));
    }

    @Test
    void should_return_company_list_when_getCompanies_by_page() throws Exception {
        String requestBody1 = """
                {
                    "name": "google"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1));

        String requestBody2 = """
                {
                    "name": "apple"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2));
        String requestBody3 = """
                {
                    "name": "microsoft"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody3));
        String requestBody4 = """
                {
                    "name": "amazon"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody4));
        String requestBody5 = """
                {
                    "name": "facebook"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody5));
        String requestBody6 = """
                {
                    "name": "tesla"
                }
                """;
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody6));

        mockMvc.perform(get("/companies?page=1&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(6))
                .andExpect(jsonPath("$[0].name").value("tesla"));
    }

    @Test
    void should_return_company_when_getCompanyById_given_exist_id() throws Exception {
        String requestBody = """
                {
                    "name": "google"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody));

        mockMvc.perform(get("/companies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("google"));
    }

}