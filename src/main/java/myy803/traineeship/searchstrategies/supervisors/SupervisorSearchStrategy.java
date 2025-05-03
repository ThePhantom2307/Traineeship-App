package myy803.traineeship.searchstrategies.supervisors;

import java.util.List;

import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;

public interface SupervisorSearchStrategy {
	List<Professor> executeSearchForSupervisors(TraineeshipPosition position);
}
