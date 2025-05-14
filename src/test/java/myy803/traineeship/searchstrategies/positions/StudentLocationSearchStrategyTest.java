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
import myy803.traineeship.model.Company;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@ExtendWith(MockitoExtension.class)
class StudentLocationSearchStrategyTest {

    @Mock 
    private TraineeshipPositionDAO traineeshipPositionDAO;
    
    @InjectMocks
    private StudentLocationSearchStrategy strategy;
    
    @Test
    void searchPositions_returnsPositionsInPreferredLocation_caseAndSpaceInsensitive() {
        Student student = mock(Student.class);
        when(student.getPreferredLocation()).thenReturn("  ThesSaLoniki ");

        Company c1 = mock(Company.class);
        when(c1.getLocation()).thenReturn("Thessaloniki");
        TraineeshipPosition pos1 = mock(TraineeshipPosition.class);
        when(pos1.getCompany()).thenReturn(c1);

        Company c2 = mock(Company.class);
        when(c2.getLocation()).thenReturn("Athens");
        TraineeshipPosition pos2 = mock(TraineeshipPosition.class);
        when(pos2.getCompany()).thenReturn(c2);

        when(traineeshipPositionDAO.findByIsAssigned(false))
                .thenReturn(List.of(pos1, pos2));

        List<TraineeshipPosition> result = strategy.searchPositions(student);

        assertEquals(1, result.size());
        assertSame(pos1, result.get(0));
    }
}
