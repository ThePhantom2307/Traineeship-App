package myy803.traineeship.searchstrategies.positions;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentInterestsAndLocationSearchStrategyTest {

    @Mock
    private StudentLocationSearchStrategy studentLocationSearchStrategy;

    @Mock
    private StudentInterestsSearchStrategy studentInterestsSearchStrategy;

    @InjectMocks
    private StudentInterestsAndLocationSearchStrategy studentInterestsAndLocationSearchStrategy;

    @Test
    public void testSearchStrategy() {
        Student student = new Student();

        TraineeshipPosition p1 = new TraineeshipPosition();
        p1.setPositionId(1);

        TraineeshipPosition p2 = new TraineeshipPosition();
        p2.setPositionId(2);

        TraineeshipPosition p3 = new TraineeshipPosition();
        p3.setPositionId(3);

        List<TraineeshipPosition> locationMatches = Arrays.asList(p1, p2);
        List<TraineeshipPosition> interestMatches = Arrays.asList(p2, p3);

        when(studentLocationSearchStrategy.searchPositions(student)).thenReturn(locationMatches);
        when(studentInterestsSearchStrategy.searchPositions(student)).thenReturn(new ArrayList<>(interestMatches)); // we need a mutable list because retainAll mutates

        List<TraineeshipPosition> result = studentInterestsAndLocationSearchStrategy.exectuteSearchForPositions(student);

        assertEquals(1, result.size());
        assertTrue(result.contains(p2));
        assertFalse(result.contains(p1));
        assertFalse(result.contains(p3));

        verify(studentLocationSearchStrategy).searchPositions(student);
        verify(studentInterestsSearchStrategy).searchPositions(student);
    }
}