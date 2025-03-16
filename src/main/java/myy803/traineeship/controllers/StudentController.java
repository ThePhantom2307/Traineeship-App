package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;
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
	    User user = userService.authenticateAndGetUser();
	    Student student = studentService.getStudent(user.getUsername());
	    model.addAttribute("profile", student);
	    return "student/profile";
	}
	
	@PostMapping("/student/save_profile")
	public String saveStudentProfile(@ModelAttribute("profile") Student student) {
	    studentService.saveStudent(student);
	    return "redirect:/student/dashboard";
	}
	
	@RequestMapping("/student/traineeship_application")
	public String applyForTraineeship(Model model) {
		User user = userService.authenticateAndGetUser();
		Student student = studentService.getStudent(user.getUsername());
		model.addAttribute("application", student);
		return "student/traineeship_application";
	}
	
	@PostMapping("/student/apply_for_traineeship")
	public String applyForTraineeship(@ModelAttribute("application") Student student) {
	    String redirection = studentService.applyForTraineeship(student);
	    return redirection;
	}
}
