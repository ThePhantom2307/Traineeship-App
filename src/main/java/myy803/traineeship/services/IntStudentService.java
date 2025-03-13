package myy803.traineeship.services;

import myy803.traineeship.dto.ApplicationDto;
import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.model.Application;
import myy803.traineeship.model.Student;

public interface IntStudentService {
	StudentDto getOrCreateStudentDto(Integer userId);
	Student getOrCreateStudent(StudentDto studentDto);
	void saveStudent(Student student);
	ApplicationDto getOrCreateTraineeshipApplicationDto(Integer studentId);
	Application getOrCreateTraineeshipApplication(ApplicationDto applicationDto);
	void saveApplication(Application application);
	Boolean isStudentExists(Integer studentId);
}
