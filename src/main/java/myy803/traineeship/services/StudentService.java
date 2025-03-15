package myy803.traineeship.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.StudentDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;

@Service
public class StudentService implements IntStudentService {	
	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	public void saveStudent(Student student) {
		studentDAO.save(student);
	}
	
	@Override
	public Boolean isStudentExists(String username) {
		Optional<Student> student = studentDAO.findByUsername(username);
		if (student.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Student getStudent(User user) {
		String username = user.getUsername();
		Optional<Student> optStudent = studentDAO.findByUsername(username);
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
	    }
	    
		return student;
	}
}
