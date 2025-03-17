package myy803.traineeship.services;

import java.util.List;

import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.TraineeshipPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.StudentDAO;
import myy803.traineeship.model.Student;

@Service
public class TraineeCommitteeServiceImpl implements TraineeCommitteeService {
	
	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private TraineeshipPositionDAO traineeshipPositionDAO;

	@Override
	public void rejectStudent(Student student) {
		student.setLookingForTraineeship(false);
		studentDAO.save(student);
	}
	
	@Override
	public List<Student> getAllPendingStudents() {
		List<Student> pendingStudents = studentDAO.findByLookingForTraineeship(true);
		return pendingStudents;
	}


	@Override
	public List<TraineeshipPosition> getAvailablePositions() {
		List<TraineeshipPosition> availablePositions = traineeshipPositionDAO.findByIsAssigned(false);
		for (TraineeshipPosition position : availablePositions) {
			System.err.println(position);
		}
		return availablePositions;
	}

}
