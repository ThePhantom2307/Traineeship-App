package myy803.traineeship.services;

import myy803.traineeship.dao.EvaluationDAO;
import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.EvaluationType;
import myy803.traineeship.model.TraineeshipPosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluationServiceImplTest {

    @Mock
    private EvaluationDAO evaluationDAO;

    @InjectMocks
    private EvaluationServiceImpl evaluationService;

    @Test
    public void testGetCompanyEvaluation_Exists() {
        TraineeshipPosition position = new TraineeshipPosition();
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.COMPANY);

        when(evaluationDAO.findByEvaluationTypeAndTraineeshipPosition(EvaluationType.COMPANY, position))
                .thenReturn(Optional.of(evaluation));

        Evaluation result = evaluationService.getCompanyEvaluation(position);

        Assertions.assertEquals(EvaluationType.COMPANY, result.getEvaluationType());
        verify(evaluationDAO).findByEvaluationTypeAndTraineeshipPosition(EvaluationType.COMPANY, position);
    }

    @Test
    public void testGetCompanyEvaluation_NotExists() {
        TraineeshipPosition position = mock(TraineeshipPosition.class);
        when(evaluationDAO.findByEvaluationTypeAndTraineeshipPosition(EvaluationType.COMPANY, position))
                .thenReturn(Optional.empty());

        Evaluation result = evaluationService.getCompanyEvaluation(position);

        Assertions.assertEquals(EvaluationType.COMPANY, result.getEvaluationType());
        verify(position).addEvaluation(result);  // Το evaluation προστίθεται στη θέση
    }

    @Test
    public void testGetSupervisorEvaluation_Exists() {
        TraineeshipPosition position = new TraineeshipPosition();
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationType(EvaluationType.SUPERVISOR);

        when(evaluationDAO.findByEvaluationTypeAndTraineeshipPosition(EvaluationType.SUPERVISOR, position))
                .thenReturn(Optional.of(evaluation));

        Evaluation result = evaluationService.getSupervisorEvaluation(position);

        Assertions.assertEquals(EvaluationType.SUPERVISOR, result.getEvaluationType());
        verify(evaluationDAO).findByEvaluationTypeAndTraineeshipPosition(EvaluationType.SUPERVISOR, position);
    }

    @Test
    public void testGetSupervisorEvaluation_NotExists() {

        TraineeshipPosition position = mock(TraineeshipPosition.class);
        when(evaluationDAO.findByEvaluationTypeAndTraineeshipPosition(EvaluationType.SUPERVISOR, position))
                .thenReturn(Optional.empty());

        Evaluation result = evaluationService.getSupervisorEvaluation(position);

        Assertions.assertEquals(EvaluationType.SUPERVISOR, result.getEvaluationType());
        verify(position).addEvaluation(result);
    }
}

