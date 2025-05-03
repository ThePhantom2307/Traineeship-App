package myy803.traineeship.services;

import java.util.List;

import myy803.traineeship.model.Company;
import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

public interface TraineeshipPositionService {
	TraineeshipPosition createNewTraineeshipPosition(Company company);
	void savePosition(TraineeshipPosition traineeshipPosition);
	void removePosition(Integer positionId);
	TraineeshipPosition getTraineeshipPosition(Integer positionId);
	List<TraineeshipPosition> getAllAdvertisedPositionsByCompany(Company company);
	void assignStudentAndSupervisor(Student student, Professor supervisor, TraineeshipPosition position);
	List<TraineeshipPosition> getAllPositionsInProgressByCompany(Company company);
	List<TraineeshipPosition> searchAndRetrieveAvailablePositions(Student student, String searchOption);
	void evaluateStudent(Evaluation evaluation);
	List<TraineeshipPosition> getAllPositionsInProgressByProfessor(Professor professor);
	List<TraineeshipPosition> getAllPositionsInProgress();
	void passStudent(Integer positionId);
	void failStudent(Integer positionId);
}
