package myy803.traineeship.searchstrategies.supervisors;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.ProfessorDAO;
import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;

@Service("supervisorLoadSearchStrategy")
public class SupervisorLoadSearchStrategy extends SupervisorSearchStrategyImpl {

    @Autowired
    private ProfessorDAO professorDAO;

    @Autowired
    private TraineeshipPositionDAO traineeshipPositionDAO;

    @Override
    protected List<Professor> searchSupervisors(TraineeshipPosition position) {
        List<Professor> professors = professorDAO.findAll();
        professors.sort(Comparator.comparingLong(
            prof -> traineeshipPositionDAO.countBySupervisorAndIsAssigned(prof, true)
        ));
        return professors;
    }
}
