package myy803.traineeship.services;

import java.util.List;

import myy803.traineeship.model.Student;

public interface TraineeCommitteeService {
	void rejectStudent(Student student);
	List<Student> getAllPendingStudents();
}
