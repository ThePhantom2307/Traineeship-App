package myy803.traineeship.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.mappers.StudentMapper;
import myy803.traineeship.mappers.UserMapper;
import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Skill;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;
import myy803.traineeship.services.IUserService;

@Controller
public class StudentController {
	@Autowired
	IUserService userService;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/student/dashboard")
	public String getStudentMainMenu(){
    	return "student/dashboard";
    }
	
	@RequestMapping("/student/profile")
	public String getProfile(Model model) {
	    int userID = authenticateAndGetUserID();

	    Optional<Student> optStudentProfile = studentMapper.findById(userID);
	    StudentDto studentDto;

	    if (optStudentProfile.isPresent()) {
	        studentDto = new StudentDto();
	        studentDto.setUserID(optStudentProfile.get().getStudentId());
	        studentDto.setFullname(optStudentProfile.get().getFullname());
	        studentDto.setUniversityID(optStudentProfile.get().getUniversityId());
	        studentDto.setPreferredLocation(optStudentProfile.get().getPreferredLocation());
	        String interestsString = optStudentProfile.get().getInterests()
	        	    .stream()
	        	    .map(Interest::getName)
	        	    .collect(Collectors.joining(", "));
	        studentDto.setInterests(interestsString);
	        
	        String skillsString = optStudentProfile.get().getSkills()
	        	    .stream()
	        	    .map(Skill::getName)
	        	    .collect(Collectors.joining(", "));
	        studentDto.setSkills(skillsString);
	    } else {
	        studentDto = new StudentDto();
	        studentDto.setUserID(userID);
	        studentDto.setFullname("");
	        studentDto.setUniversityID("");
	        studentDto.setInterests("");
	        studentDto.setSkills("");
	        studentDto.setPreferredLocation("");
	    }

	    model.addAttribute("profile", studentDto);
	    return "student/profile";
	}
	
	@PostMapping("/student/save_profile")
	public String saveStudentProfile(@ModelAttribute("profile") StudentDto studentDto) {
	    System.out.println("Saving student profile: " + studentDto.toString());

	    Optional<Student> optStudent = studentMapper.findById(studentDto.getUserID());
	    Student student;

	    if (optStudent.isPresent()) {
	        student = optStudent.get();
	    } else {
	        student = new Student();
	        User user = userMapper.findById(studentDto.getUserID()).orElseThrow(() -> new RuntimeException("User not found"));
	        student.setUser(user);
	    }
	    
	    student.setFullname(studentDto.getFullname());
	    student.setUniversityId(studentDto.getUniversityID());
	    student.setPreferredLocation(studentDto.getPreferredLocation());
	    
	    String[] studentSkills = studentDto.getSkills().split(", ");
	    List<Skill> studentSkillsList = new ArrayList<Skill>();
	    for (String s: studentSkills) {
	    	Skill skill = new Skill(s);
	    	studentSkillsList.add(skill);
	    }
	    student.setSkills(studentSkillsList);
	    
	    String[] studentInterests = studentDto.getInterests().split(", ");
	    List<Interest> studentInterestsList = new ArrayList<Interest>();
	    for (String i: studentInterests) {
	    	Interest interest = new Interest(i);
	    	studentInterestsList.add(interest);
	    }
	    student.setInterests(studentInterestsList);

	    studentMapper.save(student);

	    return "redirect:/student/profile";
	}

	
	private int authenticateAndGetUserID() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> optUser = userMapper.findByUsername(username);
		User user = optUser.get();
		int userID = user.getUserID();
		return userID;
	}
}
