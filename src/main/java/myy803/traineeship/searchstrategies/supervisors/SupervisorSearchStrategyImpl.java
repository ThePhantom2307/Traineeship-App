package myy803.traineeship.searchstrategies.supervisors;

import java.util.List;

import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;

public abstract class SupervisorSearchStrategyImpl implements SupervisorSearchStrategy {
    
    @Override
    public List<Professor> executeSearchForSupervisors(TraineeshipPosition position) {
        List<Professor> supervisors = searchSupervisors(position);
        return supervisors;
    }
    
    protected abstract List<Professor> searchSupervisors(TraineeshipPosition position);
}
