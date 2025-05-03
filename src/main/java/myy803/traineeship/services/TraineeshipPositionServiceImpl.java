package myy803.traineeship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Company;
import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;
import myy803.traineeship.searchstrategies.TraineeshipPositionSearchStrategy;


@Service
public class TraineeshipPositionServiceImpl implements TraineeshipPositionService {
	
	@Autowired
	private TraineeshipPositionDAO traineeshipPositionDAO;
	
	@Autowired
    @Qualifier("studentInterestsSearchStrategy")
    private TraineeshipPositionSearchStrategy studentInterestsSearchStrategy;
    
    @Autowired
    @Qualifier("studentLocationSearchStrategy")
    private TraineeshipPositionSearchStrategy studentLocationSearchStrategy;
    
    @Autowired
    @Qualifier("studentLocationSearchStrategy")
    private TraineeshipPositionSearchStrategy studentInterestsAndLocationSearchStrategy;
	
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
	public List<TraineeshipPosition> getAllAdvertisedPositionsByCompany(Company company) {
		List<TraineeshipPosition> advertisedTraineeshipPositions = traineeshipPositionDAO.findByCompanyAndIsAssigned(company, false);
		return advertisedTraineeshipPositions;
	}
	
	@Override
    public List<TraineeshipPosition> searchAndRetrieveAvailablePositions(Student student, String searchOption) {
        if ("interests".equalsIgnoreCase(searchOption)) {
            return studentInterestsSearchStrategy.exectuteSearchForPositions(student);
        } else if ("location".equalsIgnoreCase(searchOption)) {
            return studentLocationSearchStrategy.exectuteSearchForPositions(student);
        } else if ("both".equalsIgnoreCase(searchOption)) {
        	return studentInterestsAndLocationSearchStrategy.exectuteSearchForPositions(student);
        } else {
            return new ArrayList<TraineeshipPosition>();
        }
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
	public List<TraineeshipPosition> getAllPositionsInProgressByCompany(Company company) {
		List<TraineeshipPosition> traineeshipPositionsInProgress = traineeshipPositionDAO.findByCompanyAndIsAssigned(company, true);
		return traineeshipPositionsInProgress;
	}

	@Override
	public void evaluateStudent(Evaluation evaluation) {
		TraineeshipPosition position = evaluation.getTraineeshipPosition();
		position.addEvaluation(evaluation);
		this.savePosition(position);
	}

	@Override
	public List<TraineeshipPosition> getAllPositionsInProgressByProfessor(Professor professor) {
		List<TraineeshipPosition> traineeshipPositionsInProgress = traineeshipPositionDAO.findBySupervisorAndIsAssigned(professor, true);
		return traineeshipPositionsInProgress;
	}

	@Override
	public List<TraineeshipPosition> getAllPositionsInProgress() {
		List<TraineeshipPosition> isAssignedPositions = traineeshipPositionDAO.findByIsAssignedAndPassFailGrade(true, null);
		return isAssignedPositions;
	}

	@Override
	public void passStudent(Integer positionId) {
	    TraineeshipPosition position = this.getTraineeshipPosition(positionId);
	    position.setPassFailGrade(true);
	    this.savePosition(position);
	}

	@Override
	public void failStudent(Integer positionId) {
	    TraineeshipPosition position = this.getTraineeshipPosition(positionId);
	    position.setPassFailGrade(false);
	    this.savePosition(position);
	}

}
