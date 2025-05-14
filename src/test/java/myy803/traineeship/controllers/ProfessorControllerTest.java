package myy803.traineeship.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import myy803.traineeship.model.*;
import myy803.traineeship.services.*;

@ExtendWith(MockitoExtension.class)
class ProfessorControllerTest {

    @Mock 
    private UserService userService;
    
    @Mock
    private ProfessorService professorService;
    
    @Mock
    private TraineeshipPositionService positionService;
    
    @Mock
    private EvaluationService evaluationService;
    
    @InjectMocks
    private ProfessorController controller;

    private User user;
    private Professor professor;

    @BeforeEach
    void init() {
        user = new User(); user.setUsername("prof1");
        professor = new Professor(); professor.setUsername("prof1");
    }

    @Test
    void dashboard_returnsView() {
        assertEquals("professor/dashboard", controller.getProfessorMainMenu());
    }

    @Test
    void getProfile_populatesModel() {
        when(userService.authenticateAndGetUser()).thenReturn(user);
        when(professorService.getProfessor("prof1")).thenReturn(professor);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.getProfile(model);

        assertEquals("professor/profile", view);
        assertSame(professor, model.getAttribute("profile"));
    }

    @Test
    void saveProfile_persistsAndRedirects() {
        String view = controller.saveProfile(professor);

        verify(professorService).saveProfessor(professor);
        assertEquals("redirect:/professor/dashboard", view);
    }

    @Test
    void supervisingPositions_returnsList() {
        when(userService.authenticateAndGetUser()).thenReturn(user);
        when(professorService.getProfessor("prof1")).thenReturn(professor);
        List<TraineeshipPosition> positions = List.of(new TraineeshipPosition());
        when(positionService.getAllPositionsInProgressByProfessor(professor)).thenReturn(positions);
        ConcurrentModel model = new ConcurrentModel();

        String view = controller.traineeshipPositionsInProgres(model);

        assertEquals("professor/supervising_positions", view);
        assertSame(positions, model.getAttribute("positionsInProgress"));
    }
}
