package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.model.Student;
import myy803.traineeship.services.IntStudentService;
import myy803.traineeship.services.IntUserService;

@Controller
public class StudentController {
	@Autowired
	IntUserService userService;
	
	@Autowired
	private IntStudentService studentService;
	
	@RequestMapping("/student/dashboard")
	public String getStudentMainMenu(){
    	return "student/dashboard";
    }
	
	@RequestMapping("/student/profile")
	public String getProfile(Model model) {
	    Integer userId = userService.authenticateAndGetUserId();
	    StudentDto studentDto = studentService.getOrCreateStudentDto(userId);
	    model.addAttribute("profile", studentDto);
	    return "student/profile";
	}
	
	@PostMapping("/student/save_profile")
	public String saveStudentProfile(@ModelAttribute("profile") StudentDto studentDto) {
	    Student student = studentService.getOrCreateStudent(studentDto);
	    studentService.saveStudent(student);
	    return "redirect:/student/dashboard";
	}
	
	@RequestMapping("/student/traineeship_application")
	public String getStudentApplication(Model model){
		Integer userId = userService.authenticateAndGetUserId();
    	return "student/traineeship_application";
    }
}
