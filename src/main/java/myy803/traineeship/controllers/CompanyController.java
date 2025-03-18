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

import myy803.traineeship.model.Company;
import myy803.traineeship.model.TraineeshipPosition;
import myy803.traineeship.model.User;
import myy803.traineeship.services.CompanyService;
import myy803.traineeship.services.TraineeshipPositionService;
import myy803.traineeship.services.UserService;

@Controller
public class CompanyController {
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	TraineeshipPositionService traineeshipPositionService;
	
	@RequestMapping("/company/dashboard")
	public String getStudentMainMenu(){
    	return "company/dashboard";
    }
	
	@RequestMapping("/company/profile")
	public String getOrCreateProfile(Model model) {
		User user = userService.authenticateAndGetUser();
		String username = user.getUsername();
		Company company = companyService.getCompanyByUsername(username);
	    model.addAttribute("profile", company);
	    return "company/profile";
	}
	
	@PostMapping("/company/save_profile")
	public String saveCompanyProfile(@ModelAttribute("profile") Company company) {
		companyService.saveCompany(company);
	    return "redirect:/company/dashboard";
	}
	
	@RequestMapping("/company/available_positions")
	public String availableTraineeshipPositions(Model model) {
		User user = userService.authenticateAndGetUser();
		String username = user.getUsername();
		Company company = companyService.getCompanyByUsername(username);
		List<TraineeshipPosition> advertisedPositions = traineeshipPositionService.getAllAdvertisedPositions(company);
		model.addAttribute("advertisedPositions", advertisedPositions);
		return "company/available_positions";
	}
	
	@RequestMapping("/company/create_new_position")
	public String createNewPosition(Model model) {
		User user = userService.authenticateAndGetUser();
		String username = user.getUsername();
		if (companyService.isCompanyExists(username)) {
			Company company = companyService.getCompanyByUsername(username);
			TraineeshipPosition newTraineeshipPosition = traineeshipPositionService.createNewTraineeshipPosition(company);
			model.addAttribute("position", newTraineeshipPosition);
			return "company/create_new_position";
		} else {
			return "redirect:/company/available_positions?error_company_profile=true";
		}
	}
	
	@PostMapping("/company/save_position")
	public String saveNewPosition(@ModelAttribute("position") TraineeshipPosition traineeshipPosition) {
		traineeshipPositionService.savePosition(traineeshipPosition);
		return "redirect:/company/available_positions";
	}
	
	@GetMapping("/company/delete_position")
	public String removePosition(@RequestParam("id") Integer positionId) {
		traineeshipPositionService.removePosition(positionId);
		return "redirect:/company/available_positions";
	}
	
	@RequestMapping("/company/positions_in_progress")
	public String traineeshipPositionsInProgres(Model model) {
		User user = userService.authenticateAndGetUser();
		String username = user.getUsername();
		Company company = companyService.getCompanyByUsername(username);
		List<TraineeshipPosition> positionsInProgress = traineeshipPositionService.getAllInProgressPositions(company);
		model.addAttribute("positionsInProgress", positionsInProgress);
		return "company/positions_in_progress";
	}
}
