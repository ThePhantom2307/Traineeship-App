package myy803.traineeship.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Company;
import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;
import myy803.traineeship.searchstrategies.positions.TraineeshipPositionSearchStrategy;

@ExtendWith(MockitoExtension.class)
class TraineeshipPositionServiceImplTest {

    @Mock
    private TraineeshipPositionDAO traineeshipPositionDAO;
    
    @Mock
    private TraineeshipPositionSearchStrategy studentInterestsSearchStrategy;
    
    @Mock
    private TraineeshipPositionSearchStrategy studentLocationSearchStrategy;
    
    @Mock
    private TraineeshipPositionSearchStrategy studentInterestsAndLocationSearchStrategy;

    @InjectMocks
    private TraineeshipPositionServiceImpl sut;
    
    private Company company;
    private Student student;
    private Professor professor;
    private TraineeshipPosition position;

    @BeforeEach
    void setUp() {
        company   = mock(Company.class);
        student   = mock(Student.class);
        professor = mock(Professor.class);

        position = new TraineeshipPosition();
        position.setPositionId(1);
        position.setIsAssigned(false);
        position.setPassFailGrade(null);
    }

    @Test
    void createNewTraineeshipPosition_initializesFields() {
        TraineeshipPosition p = sut.createNewTraineeshipPosition(company);

        assertEquals("", p.getTitle());
        assertEquals("", p.getDescription());
        assertNull(p.getFromDate());
        assertNull(p.getToDate());
        assertEquals("", p.getTopics());
        assertEquals("", p.getSkills());
        assertEquals("", p.getStudentLogbook());
        assertFalse(p.getIsAssigned());
        assertNull(p.getPassFailGrade());
        assertSame(company, p.getCompany());
        assertNull(p.getStudent());
        assertNull(p.getSupervisor());
    }

    @Test
    void savePosition_delegatesToDao() {
        sut.savePosition(position);
        verify(traineeshipPositionDAO).save(position);
    }

    @Test
    void removePosition_delegatesToDao() {
        sut.removePosition(42);
        verify(traineeshipPositionDAO).deleteById(42);
    }

    @Test
    void getTraineeshipPosition_returnsEntityFromDao() {
        when(traineeshipPositionDAO.findById(1)).thenReturn(Optional.of(position));
        assertSame(position, sut.getTraineeshipPosition(1));
    }

    @Test
    void getAllAdvertisedPositionsByCompany_returnsAvailableList() {
        List<TraineeshipPosition> expected = List.of(position);
        when(traineeshipPositionDAO.findByCompanyAndIsAssigned(company, false)).thenReturn(expected);

        assertEquals(expected, sut.getAllAdvertisedPositionsByCompany(company));
    }

    @Test
    void getAllPositionsInProgressByCompany_returnsAssignedList() {
        position.setIsAssigned(true);
        List<TraineeshipPosition> expected = List.of(position);
        when(traineeshipPositionDAO.findByCompanyAndIsAssigned(company, true)).thenReturn(expected);

        List<TraineeshipPosition> actual = sut.getAllPositionsInProgressByCompany(company);

        assertEquals(expected, actual);
        verify(traineeshipPositionDAO).findByCompanyAndIsAssigned(company, true);
    }

    @Test
    void getAllPositionsInProgressByProfessor_returnsAssignedList() {
        position.setIsAssigned(true);
        List<TraineeshipPosition> expected = List.of(position);
        when(traineeshipPositionDAO.findBySupervisorAndIsAssigned(professor, true)).thenReturn(expected);

        List<TraineeshipPosition> actual = sut.getAllPositionsInProgressByProfessor(professor);

        assertEquals(expected, actual);
        verify(traineeshipPositionDAO).findBySupervisorAndIsAssigned(professor, true);
    }

    @Test
    void getAllPositionsInProgress_returnsAllAssignedWithNullGrade() {
        position.setIsAssigned(true);
        List<TraineeshipPosition> expected = List.of(position);
        when(traineeshipPositionDAO.findByIsAssignedAndPassFailGrade(true, null)).thenReturn(expected);

        assertEquals(expected, sut.getAllPositionsInProgress());
        verify(traineeshipPositionDAO).findByIsAssignedAndPassFailGrade(true, null);
    }

    @Test
    void searchAndRetrieveMatchingPositions_interestsBranch() {
        List<TraineeshipPosition> byInterests = List.of(position);
        when(studentInterestsSearchStrategy.exectuteSearchForPositions(student)).thenReturn(byInterests);

        List<TraineeshipPosition> result = sut.searchAndRetrieveMatchingPositions(student, "interests");

        assertEquals(byInterests, result);
        verify(studentInterestsSearchStrategy).exectuteSearchForPositions(student);
        verifyNoMoreInteractions(studentLocationSearchStrategy, studentInterestsAndLocationSearchStrategy);
    }

    @Test
    void searchAndRetrieveMatchingPositions_locationBranch() {
        List<TraineeshipPosition> byLocation = List.of(position);
        when(studentLocationSearchStrategy.exectuteSearchForPositions(student)).thenReturn(byLocation);

        List<TraineeshipPosition> result = sut.searchAndRetrieveMatchingPositions(student, "location");

        assertEquals(byLocation, result);
        verify(studentLocationSearchStrategy).exectuteSearchForPositions(student);
        verifyNoMoreInteractions(studentInterestsSearchStrategy, studentInterestsAndLocationSearchStrategy);
    }
    
    @Test
    void searchAndRetrieveMatchingPositions_interestsAndLocationDefault() {
        List<TraineeshipPosition> byBoth = List.of(position);
        when(studentInterestsAndLocationSearchStrategy.exectuteSearchForPositions(student)).thenReturn(byBoth);

        List<TraineeshipPosition> result = sut.searchAndRetrieveMatchingPositions(student, "somethingElse");

        assertEquals(byBoth, result);
        verify(studentInterestsAndLocationSearchStrategy).exectuteSearchForPositions(student);
        verifyNoMoreInteractions(studentInterestsSearchStrategy, studentLocationSearchStrategy);
    }

    @Test
    void assignStudentAndSupervisor_setsRelationsAndSaves() {
        sut.assignStudentAndSupervisor(student, professor, position);

        assertTrue(position.getIsAssigned());
        assertSame(student, position.getStudent());
        assertSame(professor, position.getSupervisor());

        verify(student).setLookingForTraineeship(false);
        verify(student).setTraineeshipPosition(position);
        verify(professor).addTraineeshipPosition(position);
        verify(traineeshipPositionDAO).save(position);
    }

    @Test
    void evaluateStudent_addsEvaluationAndSaves() {
        Evaluation evaluation = mock(Evaluation.class);
        when(evaluation.getTraineeshipPosition()).thenReturn(position);

        sut.evaluateStudent(evaluation);

        assertEquals(1, position.getEvaluations().size());
        assertTrue(position.getEvaluations().contains(evaluation));
        verify(traineeshipPositionDAO).save(position);
    }

    @Test
    void passStudent_setsGradeTrueAndPersists() {
        when(traineeshipPositionDAO.findById(1)).thenReturn(Optional.of(position));

        sut.passStudent(1);

        assertTrue(position.getPassFailGrade());
        verify(traineeshipPositionDAO).save(position);
    }

    @Test
    void failStudent_setsGradeFalseAndPersists() {
        when(traineeshipPositionDAO.findById(1)).thenReturn(Optional.of(position));

        sut.failStudent(1);

        assertFalse(position.getPassFailGrade());
        verify(traineeshipPositionDAO).save(position);
    }
}
