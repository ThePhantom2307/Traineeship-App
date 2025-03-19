package myy803.traineeship.services;

import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.TraineeshipPosition;

public interface EvaluationService {
	Evaluation getCompanyEvaluation(TraineeshipPosition position);
	Evaluation getSupervisorEvaluation(TraineeshipPosition position);
}
