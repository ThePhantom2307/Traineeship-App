package myy803.traineeship.services;

import java.util.List;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;

public interface IntStudentService {
	void saveStudent(Student student);
	Boolean isStudentExists(String username);
	Student getStudent(String username);
	String applyForTraineeship(Student student);
	List<Student> getAllPendingStudents();
	void rejectStudent(Student student);
}
