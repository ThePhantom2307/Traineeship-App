package myy803.traineeship.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.StudentDAO;
import myy803.traineeship.model.Student;

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
		
		if (!this.isStudentExists(student.getUsername())) {
			return "redirect:/student/traineeship_application?error=true";
		} else {
			if (student.getLookingForTraineeship()) {
				return "redirect:/student/traineeship_application?note=true";
			} else {
				student.setLookingForTraineeship(true);
				this.saveStudent(student);
				return "redirect:/student/traineeship_application?success=true";
			}
		} 
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
