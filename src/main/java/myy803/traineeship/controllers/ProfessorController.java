package myy803.traineeship.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;
import myy803.traineeship.model.User;
import myy803.traineeship.services.EvaluationService;
import myy803.traineeship.services.ProfessorService;
import myy803.traineeship.services.TraineeshipPositionService;
import myy803.traineeship.services.UserService;

@Controller
public class ProfessorController {
	@Autowired
	UserService userService;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	TraineeshipPositionService traineeshipPositionService;

	@Autowired
	EvaluationService evaluationService;
	
	@RequestMapping("/professor/dashboard")
	public String getProfessorMainMenu() {
    	return "professor/dashboard";
    }
	
	@RequestMapping("/professor/profile")
	public String getProfile(Model model) {
		User user = userService.authenticateAndGetUser();
		Professor professor = professorService.getProfessor(user.getUsername());
		model.addAttribute("profile", professor);
		return "professor/profile";
	}
	
	@PostMapping("/professor/save_profile")
	public String saveProfile(@ModelAttribute("profile") Professor professor) {
		professorService.saveProfessor(professor);
		return "redirect:/professor/dashboard";
	}
	
	@RequestMapping("/professor/supervising_positions")
	public String traineeshipPositionsInProgres(Model model) {
		User user = userService.authenticateAndGetUser();
		String username = user.getUsername();
		Professor professor = professorService.getProfessor(username);
		List<TraineeshipPosition> positionsInProgress = traineeshipPositionService.getAllPositionsInProgressByProfessor(professor);
		model.addAttribute("positionsInProgress", positionsInProgress);
		return "professor/supervising_positions";
	}
	
	@GetMapping("/professor/evaluate_student")
	public String evaluateStudent(@RequestParam("position") Integer positionId, Model model) {
		TraineeshipPosition position = traineeshipPositionService.getTraineeshipPosition(positionId);
	    Evaluation evaluation = evaluationService.getSupervisorEvaluation(position);
	    model.addAttribute("evaluation", evaluation);
	    return "professor/evaluate_student";
	}
	
	@PostMapping("/professor/submit_evaluation")
	public String submitEvaluation(@ModelAttribute("evaluation") Evaluation evaluation) {
	    traineeshipPositionService.evaluateStudent(evaluation);
	    return "redirect:/professor/supervising_positions";
	}
}
