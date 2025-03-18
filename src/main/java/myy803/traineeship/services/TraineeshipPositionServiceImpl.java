package myy803.traineeship.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Company;
import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@Service
public class TraineeshipPositionServiceImpl implements TraineeshipPositionService {
	
	@Autowired
	private TraineeshipPositionDAO traineeshipPositionDAO;
	
	@Override
	public TraineeshipPosition getTraineeshipPosition(Integer positionId) {
		Optional<TraineeshipPosition> optTraineeshipPosition = traineeshipPositionDAO.findById(positionId);
		TraineeshipPosition traineeshipPosition = optTraineeshipPosition.get();
		return traineeshipPosition;
	}
	
	@Override
	public TraineeshipPosition createNewTraineeshipPosition(Company company) {
		TraineeshipPosition newTraineeshipPosition = new TraineeshipPosition();
		newTraineeshipPosition.setTitle("");
		newTraineeshipPosition.setDescription("");
		newTraineeshipPosition.setFromDate(null);
		newTraineeshipPosition.setToDate(null);
		newTraineeshipPosition.setTopics("");
		newTraineeshipPosition.setSkills("");
		newTraineeshipPosition.setStudentLogbook("");
		newTraineeshipPosition.setPassFailGrade(null);
		newTraineeshipPosition.setIsAssigned(false);
		newTraineeshipPosition.setStudent(null);
		newTraineeshipPosition.setSupervisor(null);
		newTraineeshipPosition.setCompany(company);
		
		return newTraineeshipPosition;
	}

	@Override
	public void savePosition(TraineeshipPosition traineeshipPosition) {
		traineeshipPositionDAO.save(traineeshipPosition);
	}

	@Override
	public void removePosition(Integer positionId) {
		traineeshipPositionDAO.deleteById(positionId);
	}

	@Override
	public List<TraineeshipPosition> getAllAdvertisedPositions(Company company) {
		List<TraineeshipPosition> advertisedTraineeshipPositions = traineeshipPositionDAO.findByCompanyAndIsAssigned(company, false);
		return advertisedTraineeshipPositions;
	}
	
	@Override
	public List<TraineeshipPosition> getAvailablePositions() {
		List<TraineeshipPosition> availablePositions = traineeshipPositionDAO.findByIsAssigned(false);
		return availablePositions;
	}

	@Override
	public void assignStudentAndSupervisor(Student student, Professor supervisor, TraineeshipPosition position) {
		position.setStudent(student);
		position.setSupervisor(supervisor);
		position.setIsAssigned(true);
		student.setLookingForTraineeship(false);
		student.setTraineeshipPosition(position);
		supervisor.addTraineeshipPosition(position);
		this.savePosition(position);
	}

	@Override
	public List<TraineeshipPosition> getAllInProgressPositionsByCompany(Company company) {
		List<TraineeshipPosition> traineeshipPositionsInProgress = traineeshipPositionDAO.findByCompanyAndIsAssigned(company, true);
		return traineeshipPositionsInProgress;
	}

	@Override
	public void evaluateStudent(Evaluation evaluation) {
		TraineeshipPosition position = evaluation.getTraineeshipPosition();
		position.addEvaluation(evaluation);
		this.savePosition(position);
	}
}
