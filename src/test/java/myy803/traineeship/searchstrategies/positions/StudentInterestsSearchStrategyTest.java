package myy803.traineeship.searchstrategies.positions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@ExtendWith(MockitoExtension.class)
class StudentInterestsSearchStrategyTest {

    @Mock
    private TraineeshipPositionDAO traineeshipPositionDAO;
    
    @InjectMocks
    private StudentInterestsSearchStrategy strategy;
    
    @Test
    void searchPositions_filtersAndSortsByNumberOfCommonInterests() {
        Student student = mock(Student.class);
        when(student.getInterests()).thenReturn("AI, ML");

        TraineeshipPosition pos1 = new TraineeshipPosition();
        pos1.setTopics("AI, Databases");

        TraineeshipPosition pos2 = new TraineeshipPosition();
        pos2.setTopics("AI, ML, Big Data");

        TraineeshipPosition pos3 = new TraineeshipPosition();
        pos3.setTopics("Cyber-Security");

        when(traineeshipPositionDAO.findByIsAssigned(false)).thenReturn(List.of(pos1, pos2, pos3));

        List<TraineeshipPosition> result = strategy.searchPositions(student);

        assertEquals(List.of(pos2, pos1), result);
        assertFalse(result.contains(pos3));
    }
}
