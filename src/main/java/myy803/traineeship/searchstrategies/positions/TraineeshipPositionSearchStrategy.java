package myy803.traineeship.searchstrategies.positions;

import java.util.List;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

public interface TraineeshipPositionSearchStrategy {
	List<TraineeshipPosition> exectuteSearchForPositions(Student student);
}
