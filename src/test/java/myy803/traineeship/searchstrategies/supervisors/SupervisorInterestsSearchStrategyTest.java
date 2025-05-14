package myy803.traineeship.searchstrategies.supervisors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.traineeship.dao.ProfessorDAO;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;

@ExtendWith(MockitoExtension.class)
class SupervisorInterestsSearchStrategyTest {

    @Mock
    private ProfessorDAO professorDAO;
    
    @InjectMocks
    private SupervisorInterestsSearchStrategy strategy;

    @Test
    void searchSupervisors_returnsOnlyProfessorsWhoseInterestsContainAllTopics() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setTopics("AI , ML");

        Professor profBoth = new Professor();
        profBoth.setInterests("AI , ML , data-mining");

        Professor profPartial = new Professor();
        profPartial.setInterests("AI , databases");

        when(professorDAO.findAll()).thenReturn(List.of(profBoth, profPartial));

        List<Professor> result = strategy.executeSearchForSupervisors(position);

        assertEquals(1, result.size());
        assertSame(profBoth, result.get(0));
    }

    @Test
    void searchSupervisors_withEmptyTopicsReturnsAllProfessors() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setTopics("");

        Professor p1 = new Professor();
        Professor p2 = new Professor();
        when(professorDAO.findAll()).thenReturn(List.of(p1, p2));

        List<Professor> result = strategy.executeSearchForSupervisors(position);

        assertEquals(List.of(p1, p2), result);
    }
}
