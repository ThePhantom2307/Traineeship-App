package myy803.traineeship.services;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;

public interface IntStudentService {
	void saveStudent(Student student);
	Boolean isStudentExists(String username);
	Student getStudent(User user);
}
