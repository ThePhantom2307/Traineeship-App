package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship.dto.ApplicationDto;
import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.model.Application;
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
		ApplicationDto applicationDto = studentService.getOrCreateTraineeshipApplicationDto(userId);
		model.addAttribute("application", applicationDto);
    	return "student/traineeship_application";
    }
	
	@PostMapping("/student/save_application")
	public String saveApplication(@ModelAttribute("profile") ApplicationDto applicationDto) {
		if (studentService.isStudentExists(applicationDto.getStudentId())) {
			Application application = studentService.getOrCreateTraineeshipApplication(applicationDto);
			studentService.saveApplication(application);
			return "redirect:/student/traineeship_application";
		}
		return "redirect:/student/traineeship_application?error=true";
	}
}
