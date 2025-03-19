package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;
import myy803.traineeship.model.User;
import myy803.traineeship.services.StudentService;
import myy803.traineeship.services.TraineeshipPositionService;
import myy803.traineeship.services.UserService;

@Controller
public class StudentController {
	@Autowired
	UserService userService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	TraineeshipPositionService traineeshipPositionService;
	
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
	
	@RequestMapping("/student/logbook")
	public String editLogbook(Model model) {
		User user = userService.authenticateAndGetUser();
		Student student = studentService.getStudent(user.getUsername());
		TraineeshipPosition position = student.getTraineeshipPosition();
		if (position != null) {
			model.addAttribute("position", position);
			return "student/logbook";
		} else {
			return "redirect:/student/dashboard?logbook_error=true";
		}
	}
	
	@PostMapping("/student/save_logbook_changes")
	public String saveLogbookChanges(@ModelAttribute("position") TraineeshipPosition position) {
		traineeshipPositionService.savePosition(position);
		return "redirect:/student/logbook?changes_saved=true";
	}
}
