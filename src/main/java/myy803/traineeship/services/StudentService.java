package myy803.traineeship.services;

import myy803.traineeship.model.Student;

public interface StudentService {
	void saveStudent(Student student);
	Boolean isStudentExists(String username);
	Student getStudent(String username);
	String applyForTraineeship(Student student);
}
