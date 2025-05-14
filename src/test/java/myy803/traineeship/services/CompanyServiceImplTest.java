package myy803.traineeship.services;

import myy803.traineeship.dao.CompanyDAO;
import myy803.traineeship.model.Company;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyDAO companyDAO;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void saveCompany_delegatesToDao() {
        Company company = new Company("test_username", "test_company_name", "test_location");
        companyService.saveCompany(company);
        verify(companyDAO).save(company);
    }

    @Test
    void isCompanyExists_returnsTrueWhenDaoFindsEntity() {
        String username = "test_username";
        when(companyDAO.findByUsername(username))
                .thenReturn(Optional.of(new Company(username, "test_company_name", "test_location")));

        assertTrue(companyService.isCompanyExists(username));
    }

    @Test
    void isCompanyExists_returnsFalseWhenDaoDoesNotFindEntity() {
        String username = "missing";
        when(companyDAO.findByUsername(username)).thenReturn(Optional.empty());
        assertFalse(companyService.isCompanyExists(username));
    }

    @Test
    void getCompanyByUsername_returnsExistingCompanyWithAllFields() {
        String username = "test_username";
        Company stored = new Company(username, "test_company_name", "test_location");
        when(companyDAO.findByUsername(username)).thenReturn(Optional.of(stored));
        Company retrieved = companyService.getCompanyByUsername(username);
        assertThat(retrieved)
                .usingRecursiveComparison()
                .ignoringFields("traineeshipPositions")
                .isEqualTo(stored);
    }

    @Test
    void getCompanyByUsername_returnsDefaultStubWhenCompanyMissing() {
        String username = "newCo";
        when(companyDAO.findByUsername(username)).thenReturn(Optional.empty());
        Company retrieved = companyService.getCompanyByUsername(username);
        assertAll("default company stub should have username and empty strings", 
                () -> assertEquals(username, retrieved.getUsername()),
                () -> assertEquals("", retrieved.getCompanyName()),
                () -> assertEquals("", retrieved.getLocation())
        );
    }
}
