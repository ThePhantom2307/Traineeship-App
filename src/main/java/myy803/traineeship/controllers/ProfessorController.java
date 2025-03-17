package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeship.model.Professor;
import myy803.traineeship.model.User;
import myy803.traineeship.services.ProfessorService;
import myy803.traineeship.services.UserService;

@Controller
public class ProfessorController {
	@Autowired
	UserService userService;
	
	@Autowired
	ProfessorService professorService;
	
	@RequestMapping("/professor/dashboard")
	public String getProfessorMainMenu() {
    	return "professor/dashboard";
    }
	
	@RequestMapping("/professor/profile")
	public String getProfile(Model model) {
		User user = userService.authenticateAndGetUser();
		Professor professor = professorService.getProfessor(user);
		model.addAttribute("profile", professor);
		return "professor/profile";
	}
	
	@PostMapping("/professor/save_profile")
	public String saveProfile(@ModelAttribute("profile") Professor professor) {
		professorService.saveProfessor(professor);
		return "redirect:/professor/dashboard";
	}
}
