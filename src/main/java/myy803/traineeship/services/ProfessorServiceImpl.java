package myy803.traineeship.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.ProfessorDAO;
import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;
import myy803.traineeship.searchstrategies.supervisors.SupervisorSearchStrategy;

@Service
public class ProfessorServiceImpl implements ProfessorService{
	
	@Autowired
	private ProfessorDAO professorDAO;
	
	@Autowired
	private TraineeshipPositionDAO traineeshipPositionDAO;
	
	@Autowired
    @Qualifier("supervisorInterestsSearchStrategy")
    private SupervisorSearchStrategy supervisorInterestsSearchStrategy;

	@Autowired
	@Qualifier("supervisorLoadSearchStrategy")
	private SupervisorSearchStrategy supervisorLoadSearchStrategy;
	
	@Override
	public void saveProfessor(Professor professor) {
		professorDAO.save(professor);
	}
	
	@Override
	public Boolean isProfessorExists(String username) {
		Optional<Professor> student = professorDAO.findByUsername(username);
		if (student.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Professor getProfessor(String username) {
		Optional<Professor> optProfessor = professorDAO.findByUsername(username);
	    Professor professor;
	    
	    if (optProfessor.isPresent()) {
	    	professor = optProfessor.get();
	    } else {
	    	professor = new Professor();
	    	professor.setUsername(username);
	        professor.setFullname("");
	        professor.setInterests("");
	    }
	    
		return professor;
	}

	@Override
	public List<Professor> searchAndRetrieveMatchingSupervisors(Integer positionId, String searchOption) {
		Optional<TraineeshipPosition> positions = traineeshipPositionDAO.findById(positionId);
		TraineeshipPosition selectedPosition = positions.get();
		if ("interests".equalsIgnoreCase(searchOption)) {
			return supervisorInterestsSearchStrategy.executeSearchForSupervisors(selectedPosition);
		} else {
			return supervisorLoadSearchStrategy.executeSearchForSupervisors(selectedPosition);
		}
	}
}
