package myy803.traineeship.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import myy803.traineeship.model.*;
import myy803.traineeship.services.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Mock
    private UserService userService;
    
    @Mock
    private CompanyService companyService;
    
    @Mock
    private TraineeshipPositionService positionService;
    
    @Mock
    private EvaluationService evaluationService;
    
    @InjectMocks
    private CompanyController controller;

    private User user;
    private Company company;

    @BeforeEach
    void init() {
        user = new User();
        user.setUsername("test_username");
        company = new Company();
        company.setUsername("test_username");
    }

    @Test
    void dashboard_returnsCorrectView() {
        assertEquals("company/dashboard", controller.getStudentMainMenu());
    }

    @Test
    void getOrCreateProfile_addsProfileToModel() {
        when(userService.authenticateAndGetUser()).thenReturn(user);
        when(companyService.getCompanyByUsername("test_username")).thenReturn(company);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.getOrCreateProfile(model);

        assertEquals("company/profile", view);
        assertSame(company, model.getAttribute("profile"));
    }

    @Test
    void saveCompanyProfile_persistsAndRedirects() {
        String view = controller.saveCompanyProfile(company);

        verify(companyService).saveCompany(company);
        assertEquals("redirect:/company/dashboard", view);
    }

    @Test
    void createNewPosition_whenCompanyExists_returnsForm() {
        when(userService.authenticateAndGetUser()).thenReturn(user);
        when(companyService.isCompanyExists("test_username")).thenReturn(true);
        when(companyService.getCompanyByUsername("test_username")).thenReturn(company);
        TraineeshipPosition pos = new TraineeshipPosition();
        when(positionService.createNewTraineeshipPosition(company)).thenReturn(pos);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.createNewPosition(model);

        assertEquals("company/create_new_position", view);
        assertSame(pos, model.getAttribute("position"));
    }

    @Test
    void createNewPosition_whenCompanyMissing_redirectsWithError() {
        when(userService.authenticateAndGetUser()).thenReturn(user);
        when(companyService.isCompanyExists("test_username")).thenReturn(false);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.createNewPosition(model);

        assertEquals("redirect:/company/available_positions?error_company_profile=true", view);
    }

    @Test
    void saveNewPosition_callsServiceAndRedirects() {
        TraineeshipPosition pos = new TraineeshipPosition();

        String view = controller.saveNewPosition(pos);

        verify(positionService).savePosition(pos);
        assertEquals("redirect:/company/available_positions", view);
    }

    @Test
    void removePosition_deletesAndRedirects() {
        String view = controller.removePosition(33);

        verify(positionService).removePosition(33);
        assertEquals("redirect:/company/available_positions", view);
    }
}
