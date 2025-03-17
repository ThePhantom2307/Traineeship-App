package myy803.traineeship.services;

import java.util.List;

import myy803.traineeship.model.Company;
import myy803.traineeship.model.TraineeshipPosition;

public interface TraineeshipPositionService {
	TraineeshipPosition createNewTraineeshipPosition(Company company);
	void savePosition(TraineeshipPosition traineeshipPosition);
	void removePosition(Integer positionId);
	TraineeshipPosition getTraineeshipPosition(Integer positionId);
	List<TraineeshipPosition> getAllAdvertisedPositions(Company company);
}
