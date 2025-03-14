package myy803.traineeship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.mappers.ApplicationMapper;
import myy803.traineeship.mappers.StudentMapper;
import myy803.traineeship.model.Application;
import myy803.traineeship.model.ApplicationStatus;
import myy803.traineeship.model.Student;

@Service
public class TraineeshipCommitteeService implements IntTraineeshipCommitteeService {
	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	ApplicationMapper applicationMapper;
	
	@Override
	public List<Student> getAllPendingApplications() {
		List<Application> pendingApplications = applicationMapper.findByStatus(ApplicationStatus.PENDING);
		List<Student> pendingStudents = new ArrayList<Student>();
		
		for (Application application: pendingApplications) {
			Student student = application.getStudent();
			pendingStudents.add(student);
		}
		
		return pendingStudents;
	}

	@Override
	public void rejectStudent(Integer studentId) {
		Optional<Application> optApplication = applicationMapper.findByStudent_StudentId(studentId);
		Application application = optApplication.get();
		application.setStatus(ApplicationStatus.REJECTED);
		applicationMapper.save(application);
	}
}
