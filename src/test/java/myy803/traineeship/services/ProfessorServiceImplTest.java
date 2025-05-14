package myy803.traineeship.services;

import myy803.traineeship.dao.ProfessorDAO;
import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;
import myy803.traineeship.searchstrategies.supervisors.SupervisorSearchStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceImplTest {

    @Mock
    private ProfessorDAO professorDAO;

    @Mock
    private TraineeshipPositionDAO traineeshipPositionDAO;

    @Mock
    private SupervisorSearchStrategy supervisorInterestsSearchStrategy;

    @Mock
    private SupervisorSearchStrategy supervisorLoadSearchStrategy;
    
    @InjectMocks
    private ProfessorService professorService = new ProfessorServiceImpl();

    @Test
    public void testSaveProfessor() {
        Professor p = new Professor();
        p.setUsername("test_username");

        professorService.saveProfessor(p);

        verify(professorDAO, times(1)).save(p);
    }

    @Test
    public void testIsProfessorExists_True() {
        when(professorDAO.findByUsername("test_username")).thenReturn(Optional.of(new Professor()));

        Assertions.assertTrue(professorService.doesProfessorExists("test_username"));
        verify(professorDAO).findByUsername("test_username");
    }

    @Test
    public void testIsProfessorExists_False() {
        when(professorDAO.findByUsername("test_username")).thenReturn(Optional.empty());

        Assertions.assertFalse(professorService.doesProfessorExists("test_username"));
        verify(professorDAO).findByUsername("test_username");
    }

    @Test
    public void testGetProfessor_Exists() {
        Professor p = new Professor();
        p.setUsername("test_username");
        p.setFullname("test_fullname");

        when(professorDAO.findByUsername("test_username")).thenReturn(Optional.of(p));

        Professor result = professorService.getProfessor("test_username");

        Assertions.assertEquals("test_username", result.getUsername());
        Assertions.assertEquals("test_fullname", result.getFullname());
        verify(professorDAO).findByUsername("test_username");
    }

    @Test
    public void testGetProfessor_NotExists() {
        when(professorDAO.findByUsername("unknown")).thenReturn(Optional.empty());

        Professor result = professorService.getProfessor("unknown");

        Assertions.assertEquals("unknown", result.getUsername());
        Assertions.assertEquals("", result.getFullname());
        Assertions.assertEquals("", result.getInterests());
        verify(professorDAO).findByUsername("unknown");
    }

    @Test
    public void testSearchAndRetrieveMatchingSupervisors_Interests() {
        TraineeshipPosition position = new TraineeshipPosition();
        when(traineeshipPositionDAO.findById(1)).thenReturn(Optional.of(position));

        List<Professor> expected = Arrays.asList(new Professor(), new Professor());
        when(supervisorInterestsSearchStrategy.executeSearchForSupervisors(position)).thenReturn(expected);

        List<Professor> result = professorService.searchAndRetrieveMatchingSupervisors(1, "interests");

        Assertions.assertEquals(2, result.size());
        verify(supervisorInterestsSearchStrategy).executeSearchForSupervisors(position);
    }

    @Test
    public void testSearchAndRetrieveMatchingSupervisors_Load() {
        TraineeshipPosition position = new TraineeshipPosition();
        when(traineeshipPositionDAO.findById(2)).thenReturn(Optional.of(position));

        List<Professor> expected = Collections.singletonList(new Professor());
        when(supervisorLoadSearchStrategy.executeSearchForSupervisors(position)).thenReturn(expected);

        List<Professor> result = professorService.searchAndRetrieveMatchingSupervisors(2, "something_else");

        Assertions.assertEquals(1, result.size());
        verify(supervisorLoadSearchStrategy).executeSearchForSupervisors(position);
    }
}