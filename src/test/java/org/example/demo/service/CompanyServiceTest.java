package org.example.demo.service;

import org.example.demo.model.Company;
import org.example.demo.repository.CompanyRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void should_throw_exception_when_get_company_given_non_existing_id() {
        when(companyRepository.findById(anyLong())).thenReturn(null);
        Exception exception = assertThrows(CompanyNotFoundException.class, () -> {
            companyService.getCompany(5);
        });
        assertEquals("Company with id 5 not found", exception.getMessage());
        verify(companyRepository, times(1)).findById(anyLong());
    }

    @Test
    void should_return_company_when_get_company_given_existing_id() {
        Company company = new Company();
        company.setId(1);
        company.setName("TechCorp");
        when(companyRepository.findById(1L)).thenReturn(company);
        Company foundCompany = companyService.getCompany(1);
        assertEquals(company, foundCompany);
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    void should_return_companies_when_get_companies_given_page_and_size() {
        Company company1 = new Company();
        company1.setId(1);
        company1.setName("TechCorp");
        Company company2 = new Company();
        company2.setId(2);
        company2.setName("BizInc");
        Company company3 = new Company();
        company2.setId(3);
        company2.setName("Apple");
        when(companyRepository.findByPageAndSize(1, 2)).thenReturn(List.of(company1, company2));
        var companies = companyService.getCompanies(1, 2);
        assertEquals(2, companies.size());
        assertEquals(company1, companies.get(0));
        assertEquals(company2, companies.get(1));
        verify(companyRepository, times(1)).findByPageAndSize(1, 2);
    }

    @Test
    void should_return_all_companies_when_get_companies_given_no_page_and_size() {
        Company company1 = new Company();
        company1.setId(1);
        company1.setName("TechCorp");
        Company company2 = new Company();
        company2.setId(2);
        company2.setName("BizInc");
        Company company3 = new Company();
        company2.setId(3);
        company2.setName("Apple");
        when(companyRepository.findAll()).thenReturn(List.of(company1, company2, company3));
        var companies = companyService.getCompanies(null, null);
        assertEquals(3, companies.size());
        assertEquals(company1, companies.get(0));
        assertEquals(company2, companies.get(1));
        assertEquals(company3, companies.get(2));
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void should_return_company_when_create_company_given_valid_company() {
        Company company = new Company();
        company.setName("TechCorp");
        when(companyRepository.save(company)).thenReturn(company);
        Company createdCompany = companyService.createCompany(company);
        assertEquals(company, createdCompany);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void should_delete_company_when_delete_company_given_existing_id() {
        doNothing().when(companyRepository).deleteById(1);
        companyService.deleteCompany(1);
        verify(companyRepository, times(1)).deleteById(1);
    }

    @Test
    void should_return_updated_company_when_update_company_given_existing_id_and_valid_updates() {
        Company company = new Company();
        company.setId(1);
        company.setName("TechCorp");
        when(companyRepository.findById(1L)).thenReturn(company);
        doAnswer(invocation -> {
            Company comp = invocation.getArgument(0);
            String newName = invocation.getArgument(1);
            comp.setName(newName);
            return null;
        }).when(companyRepository).updateName(eq(company), anyString());
        Company updates = new Company();
        updates.setName("NewTechCorp");
        Company updatedCompany = companyService.updateCompany(1, updates);
        assertEquals("NewTechCorp", updatedCompany.getName());
        verify(companyRepository, times(1)).findById(1L);
        verify(companyRepository, times(1)).updateName(eq(company), eq("NewTechCorp"));
    }
}