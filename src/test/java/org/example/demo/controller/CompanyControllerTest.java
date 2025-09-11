package org.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo.model.Company;
import org.example.demo.model.Employee;
import org.example.demo.repository.CompanyRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        companyRepository.setUp();
    }

    private long createCompany(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform((post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }

    @Test
    void should_return_company_when_create_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "google"
                }
                """;
        long id = createCompany(requestBody);
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id + 1));
    }

    @Test
    void should_return_company_list_when_getCompanies() throws Exception {
        String requestBody1 = """
                {
                    "name": "google"
                }
                """;
        long id1 = createCompany(requestBody1);

        String requestBody2 = """
                {
                    "name": "apple"
                }
                """;
        long id2 = createCompany(requestBody2);

        mockMvc.perform(get("/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value("google"))
                .andExpect(jsonPath("$[1].id").value(id2))
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
        long id = createCompany(requestBody6);

        mockMvc.perform(get("/companies?page=1&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value("tesla"));
    }

    @Test
    void should_return_company_when_getCompanyById_given_exist_id() throws Exception {
        String requestBody = """
                {
                    "name": "google"
                }
                """;
        long id = createCompany(requestBody);

        mockMvc.perform(get("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("google"));
    }

    @Test
    void should_return_company_when_updateCompany_given_exist_id_and_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "google"
                }
                """;
        long id = createCompany(requestBody);

        String updateRequestBody = """
                {
                    "name": "alphabet"
                }
                """;
        mockMvc.perform(put("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("alphabet"));
    }

    @Test
    void should_return_no_content_when_deleteCompany_given_exist_id() throws Exception {
        String requestBody = """
                {
                    "name": "google"
                }
                """;
        long id = createCompany(requestBody);

        mockMvc.perform(delete("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_update_employee_when_update_given_a_valid_employee() throws Exception {
        Company company = new Company();
        company.setName("apple");
        companyRepository.save(company);

        Employee employee = new Employee();
        employee.setName("Tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(20000.0);
        employee.setActive(true);
        employee.setCompanyId(company.getId());
        employeeRepository.save(employee);

        String updateRequestBody = """
                {
                    "name": "Jerry",
                    "age": 18,
                    "gender": "Female",
                    "salary": 30000.0
                }
                """;
        mockMvc.perform(put("/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("Jerry"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(30000.0))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.companyId").value(company.getId()));
    }
}