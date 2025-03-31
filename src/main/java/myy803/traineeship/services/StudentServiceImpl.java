package myy803.traineeship.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.StudentDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@Service
public class StudentServiceImpl implements StudentService {	
	@Autowired
	private StudentDAO studentDAO;

	@Override
	public void saveStudent(Student student) {
		studentDAO.save(student);
	}

	private Boolean isStudentExists(String username) {
		Optional<Student> optStudent = studentDAO.findByUsername(username);
		if (optStudent.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Student getStudent(String username) {
		Optional<Student> optStudent =  studentDAO.findByUsername(username);
	    Student student;
	    
	    if (optStudent.isPresent()) {
	        student = optStudent.get();
	    } else {
	        student = new Student();
	        student.setUsername(username);
	        student.setFullname("");
	        student.setUniversityId("");
	        student.setInterests("");
	        student.setSkills("");
	        student.setPreferredLocation("");
	        student.setLookingForTraineeship(false);
	        student.setAverageGrade(0.0);
	    }
	    
		return student;
	}
	
	@Override
	public String applyForTraineeship(Student student) {
		String username = student.getUsername();
		
		if (!this.isStudentExists(username)) {
			return "redirect:/student/traineeship_application?error=true";
		} else if (hasStudentFinishedTheTraineeship(student)) {
			return "redirect:/student/traineeship_application?traineeship_finished=true";
		} else if (isStudentAssignedForTraineeship(student)) {
			return "redirect:/student/traineeship_application?already_assigned=true";
		} else if (isStudentLookingForTraineeship(student)) {
			return "redirect:/student/traineeship_application?note=true";
		} else {
			student.setLookingForTraineeship(true);
			this.saveStudent(student);
			return "redirect:/student/traineeship_application?success=true";
		}
	}
	
	private Boolean hasStudentFinishedTheTraineeship(Student student) {
		TraineeshipPosition assignedPosition = student.getTraineeshipPosition();
		if (assignedPosition != null) {
			Boolean passFailGrade = assignedPosition.getPassFailGrade();
			if (passFailGrade != null) {
				return true;
			}
			return false;
		}
		
		return false;
	}
	
	private Boolean isStudentAssignedForTraineeship(Student student) {
		TraineeshipPosition assignedPosition = student.getTraineeshipPosition();
		if (assignedPosition != null) {
			return true;
		}
		
		return false;
	}
	
	private Boolean isStudentLookingForTraineeship(Student student) {
		return student.getLookingForTraineeship();
	}
	
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
}
