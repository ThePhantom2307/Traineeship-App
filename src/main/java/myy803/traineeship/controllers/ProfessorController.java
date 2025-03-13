package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeship.dto.ProfessorDto;
import myy803.traineeship.model.Professor;
import myy803.traineeship.services.IntProfessorService;
import myy803.traineeship.services.IntUserService;

@Controller
public class ProfessorController {
	@Autowired
	IntUserService userService;
	
	@Autowired
	IntProfessorService professorService;
	
	@RequestMapping("/professor/dashboard")
	public String getProfessorMainMenu() {
    	return "/professor/dashboard";
    }
	
	@RequestMapping("/professor/profile")
	public String getProfile(Model model) {
		Integer userId = userService.authenticateAndGetUserId();
		ProfessorDto professorDto = professorService.getOrCreateProfessorDto(userId);
		model.addAttribute("profile", professorDto);
		return "professor/profile";
	}
	
	@PostMapping("/professor/save_profile")
	public String saveProfile(@ModelAttribute("profile") ProfessorDto professorDto) {
		Professor professor = professorService.getOrCreateProfessor(professorDto);
		professorService.saveProfessor(professor);
		return "redirect:/professor/dashboard";
	}
}
