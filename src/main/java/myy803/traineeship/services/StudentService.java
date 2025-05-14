package myy803.traineeship.services;

import java.util.List;

import myy803.traineeship.model.Student;

public interface StudentService {
	void saveStudent(Student student);
	public Boolean doesStudentExist(String username);
	Student getStudent(String username);
	String applyForTraineeship(Student student);
	void rejectStudent(Student student);
	List<Student> getAllPendingStudents();
}
