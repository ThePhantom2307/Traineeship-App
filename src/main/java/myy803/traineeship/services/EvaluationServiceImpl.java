package myy803.traineeship.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.EvaluationDAO;
import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.EvaluationType;
import myy803.traineeship.model.TraineeshipPosition;

@Service
public class EvaluationServiceImpl implements EvaluationService {
	
	@Autowired
	TraineeshipPositionDAO traineeshipPositionDAO;
	
	@Autowired
	EvaluationDAO evaluationDAO;

	@Override
	public Evaluation getCompanyEvaluation(TraineeshipPosition position) {
		Optional<Evaluation> optCompanyEvaluation = evaluationDAO.findByEvaluationTypeAndTraineeshipPosition(EvaluationType.COMPANY, position);
		Evaluation companyEvaluation;
		
		if (optCompanyEvaluation.isPresent()) {
			companyEvaluation = optCompanyEvaluation.get();
		} else {
			companyEvaluation = new Evaluation();
			companyEvaluation.setEvaluationType(EvaluationType.COMPANY);
			position.addEvaluation(companyEvaluation);
		}
		
		return companyEvaluation;
	}

	@Override
	public Evaluation getSupervisorEvaluation(TraineeshipPosition position) {
		Optional<Evaluation> optSupervisorEvaluation = evaluationDAO.findByEvaluationTypeAndTraineeshipPosition(EvaluationType.SUPERVISOR, position);
		Evaluation supervisorEvaluation;
		
		if (optSupervisorEvaluation.isPresent()) {
			supervisorEvaluation = optSupervisorEvaluation.get();
		} else {
			supervisorEvaluation = new Evaluation();
			supervisorEvaluation.setEvaluationType(EvaluationType.SUPERVISOR);
			position.addEvaluation(supervisorEvaluation);
		}
		
		return supervisorEvaluation;
	}

}
