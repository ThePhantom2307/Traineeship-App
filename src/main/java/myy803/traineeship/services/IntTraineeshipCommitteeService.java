package myy803.traineeship.services;

import java.util.List;

import myy803.traineeship.model.Student;

public interface IntTraineeshipCommitteeService {
	List<Student> getAllPendingApplications();
	void rejectStudent(Integer studentId);
}
