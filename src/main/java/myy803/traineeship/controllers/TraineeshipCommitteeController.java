package myy803.traineeship.controllers;


import java.util.List;

import myy803.traineeship.model.TraineeshipPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import myy803.traineeship.model.Professor;
import myy803.traineeship.model.Student;
import myy803.traineeship.services.ProfessorService;
import myy803.traineeship.services.StudentService;
import myy803.traineeship.services.TraineeshipPositionService;
import myy803.traineeship.services.UserService;

@Controller
public class TraineeshipCommitteeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TraineeshipPositionService traineeshipPositionService;

	@RequestMapping("/trainee_committee/dashboard")
	public String getStudentMainMenu(){
    	return "trainee_committee/dashboard";
    }
	
	@RequestMapping("/trainee_committee/students_applications")
	public String displayPendingStudentsApplications(Model model) {
		userService.authenticateAndGetUser();
		List<Student> pendingStudents = studentService.getAllPendingStudents();
		model.addAttribute("studentsApplications", pendingStudents);
		return "trainee_committee/students_applications";
	}
	
	@GetMapping("/trainee_committee/reject")
	public String rejectStudent(@RequestParam("username") String username) {
		Student student = studentService.getStudent(username);
		studentService.rejectStudent(student);
	    return "redirect:/trainee_committee/students_applications";
	}
	
	@GetMapping("/trainee_committee/accept")
	public String acceptStudent(@RequestParam("username") String username, RedirectAttributes redirectAttributes) {
	    Student student = studentService.getStudent(username);
	    redirectAttributes.addFlashAttribute("student", student);
	    return "redirect:/trainee_committee/find_position";
	}

	@RequestMapping("/trainee_committee/find_position")
	public String displayAvailablePositions(Model model) {
	    userService.authenticateAndGetUser();
	    List<TraineeshipPosition> availablePositions = traineeshipPositionService.getAvailablePositions();
	    model.addAttribute("availablePositions", availablePositions);
	    return "trainee_committee/find_position";
	}
	
	@GetMapping("/trainee_committee/select_position")
	public String selectPosition(@RequestParam("position") Integer positionId,
	                             @RequestParam("student_username") String studentUsername,
	                             RedirectAttributes redirectAttributes) {
	    Student student = studentService.getStudent(studentUsername);
	    redirectAttributes.addFlashAttribute("student", student);
	    redirectAttributes.addFlashAttribute("positionId", positionId);
	    return "redirect:/trainee_committee/find_supervisor";
	}

	
	@RequestMapping("/trainee_committee/find_supervisor")
	public String displayProfessors(Model model) {
	    if (!model.containsAttribute("positionId") || !model.containsAttribute("student")) {
	        throw new IllegalArgumentException("Required data is missing");
	    }
	    Integer positionId = (Integer) model.asMap().get("positionId");
	    Student student = (Student) model.asMap().get("student");
	    model.addAttribute("positionId", positionId);
	    model.addAttribute("student", student);
	    
	    List<Professor> professors = professorService.getAllProfessors();
	    model.addAttribute("professors", professors);
	    return "trainee_committee/find_supervisor";
	}

	
	@GetMapping("/trainee_committee/select_supervisor")
	public String selectSupervisor(@RequestParam("position") Integer positionId,
	                               @RequestParam("student_username") String studentUsername,
	                               @RequestParam("professor_username") String professorUsername,
	                               RedirectAttributes redirectAttributes) {
	    Student student = studentService.getStudent(studentUsername);
	    Professor supervisor = professorService.getProfessor(professorUsername);
	    TraineeshipPosition position = traineeshipPositionService.getTraineeshipPosition(positionId);
	    redirectAttributes.addFlashAttribute("student", student);
	    redirectAttributes.addFlashAttribute("position", position);
	    redirectAttributes.addFlashAttribute("professor", supervisor);
	    return "redirect:/trainee_committee/preview_details";
	}
	
	@RequestMapping("/trainee_committee/preview_details")
	public String previewDetailsBeforeAssignment(Model model, RedirectAttributes redirectAttributes) {
	    if (!model.containsAttribute("position")) {
	        redirectAttributes.addFlashAttribute("error", "No position selected. Please try again.");
	        return "redirect:/trainee_committee/find_position";
	    }
	    return "trainee_committee/preview_details";
	}
	
	@GetMapping("/trainee_committee/assign_student")
	public String assignStudentToPosition(@RequestParam("position") Integer positionId,
	                             @RequestParam("student_username") String studentUsername,
	                             @RequestParam("professor_username") String professorUsername,
	                             RedirectAttributes redirectAttributes) {
	    Student student = studentService.getStudent(studentUsername);
	    Professor supervisor = professorService.getProfessor(professorUsername);
	    TraineeshipPosition position = traineeshipPositionService.getTraineeshipPosition(positionId);
	    traineeshipPositionService.assignStudentAndSupervisor(student, supervisor, position);
	    return "redirect:/trainee_committee/students_applications";
	}
}
