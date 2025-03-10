package myy803.traineeship.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.mappers.StudentProfileMapper;
import myy803.traineeship.mappers.UserMapper;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;
import myy803.traineeship.services.IUserService;

@Controller
public class StudentController {
	@Autowired
	IUserService userService;
	
	@Autowired
	StudentProfileMapper studentProfileMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/student/dashboard")
	public String getStudentMainMenu(){
    	return "/student/dashboard";
    }
	
	@RequestMapping("/student/profile")
	public String getProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> optUser = userMapper.findByUsername(username);
		User user = optUser.get();
		int userID = user.getUserID();
		
		Optional<Student> optStudentProfile = studentProfileMapper.findById(userID);
		Student student = null;
		StudentDto studentDto = null;
		if (optStudentProfile.isPresent()) {
			student = optStudentProfile.get();
			studentDto = student.createStudentDto();
		} else {
			studentDto = new StudentDto();
		}
		
		model.addAttribute("profile", studentDto);
		return "/student/profile";
	}
	
	@RequestMapping("/student/save_profile")
	public String saveStudentProfile(@ModelAttribute("profile") StudentDto studentDto, Model theModel) {
	    System.out.println("Trying to find User with ID: " + studentDto.getUserID());

	    Optional<User> optUser = userMapper.findById(studentDto.getUserID());
	    
	    if (!optUser.isPresent()) {
	        System.err.println("User not found in database!");
	        throw new RuntimeException("User not found");
	    }

	    User user = optUser.get();
	    System.out.println("Found User: " + user.getUsername());

	    Optional<Student> optStudentProfile = studentProfileMapper.findById(studentDto.getUserID());
	    Student studentProfile = optStudentProfile.orElse(new Student());

	    studentProfile.setUser(user);

	    studentProfile.setFullname(studentDto.getFullname());
	    studentProfile.setUniversityID(studentDto.getUniversityID());
	    studentProfile.setPreferredLocation(studentDto.getPreferredLocation());

	    studentProfileMapper.save(studentProfile);
	    return "/student/dashboard";
	}


}
