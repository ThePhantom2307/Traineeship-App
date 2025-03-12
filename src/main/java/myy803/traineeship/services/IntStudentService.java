package myy803.traineeship.services;

import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.model.Student;

public interface IntStudentService {
	Student getOrCreateStudent(StudentDto studentDto);
	StudentDto getOrCreateStudentDto(Integer userId);
	void saveStudent(Student student);
}
