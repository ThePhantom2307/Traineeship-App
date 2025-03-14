package myy803.traineeship.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.model.Student;
import myy803.traineeship.services.IntStudentService;
import myy803.traineeship.services.IntTraineeshipCommitteeService;
import myy803.traineeship.services.IntUserService;

@Controller
public class TraineeshipCommitteeController {
	@Autowired
	IntUserService userService;
	
	@Autowired
	IntStudentService studentService;
	
	@Autowired
	IntTraineeshipCommitteeService traineeshipCommitteeService;
	
	@RequestMapping("/trainee_committee/dashboard")
	public String getStudentMainMenu(){
    	return "trainee_committee/dashboard";
    }
	
	@RequestMapping("/trainee_committee/students_applications")
	public String getStudentsApplications(Model model) {
		List<Student> pendingStudents = traineeshipCommitteeService.getAllPendingApplications();
		List<StudentDto> pendingStudentsDto = new ArrayList<StudentDto>();
		for (Student student: pendingStudents) {
			StudentDto studentDto = studentService.getOrCreateStudentDto(student.getStudentId());
			pendingStudentsDto.add(studentDto);
		}
		model.addAttribute("studentsApplications", pendingStudentsDto);
		return "trainee_committee/students_applications";
	}
	
	@RequestMapping("/reject")
	public String rejectStudentApplication(@RequestParam("id") Integer studentId) {
		traineeshipCommitteeService.rejectStudent(studentId);
		return "redirect:/trainee_committee/students_applications";
	}
}
