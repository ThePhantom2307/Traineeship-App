package myy803.traineeship.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.traineeship.model.Student;
import myy803.traineeship.services.StudentService;
import myy803.traineeship.services.TraineeCommitteeService;
import myy803.traineeship.services.UserService;

@Controller
public class TraineeshipCommitteeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TraineeCommitteeService traineeCommitteeService;

	@RequestMapping("/trainee_committee/dashboard")
	public String getStudentMainMenu(){
    	return "trainee_committee/dashboard";
    }
	
	@RequestMapping("/trainee_committee/students_applications")
	public String displayPendingStudentsApplications(Model model) {
		userService.authenticateAndGetUser();
		List<Student> pendingStudents = traineeCommitteeService.getAllPendingStudents();
		model.addAttribute("studentsApplications", pendingStudents);
		return "trainee_committee/students_applications";
	}
	
	@GetMapping("/reject")
	public String rejectStudent(@RequestParam("username") String username) {
		Student student = studentService.getStudent(username);
		traineeCommitteeService.rejectStudent(student);
	    return "redirect:/trainee_committee/students_applications";
	}
}
