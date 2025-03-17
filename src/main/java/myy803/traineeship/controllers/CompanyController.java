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
	
	@RequestMapping("/company/dashboard")
	public String getStudentMainMenu(){
    	return "company/dashboard";
    }
	
	@RequestMapping("/company/profile")
	public String getOrCreateProfile(Model model) {
	    User user = userService.authenticateAndGetUser();
	    Company company = companyService.getCompany(user);
	    model.addAttribute("profile", company);
	    return "company/profile";
	}
	
	@PostMapping("/company/save_profile")
	public String saveStudentProfile(@ModelAttribute("profile") Company company) {
		companyService.saveCompany(company);
	    return "redirect:/company/dashboard";
	}
	
	@RequestMapping("/company/available_positions")
	public String availableTraineeshipPositions(Model model) {
		/*User user = userService.authenticateAndGetUser();
		Company company = companyService.getCompany(user);
		List<TraineeshipPosition> advertisedPositions = traineeshipPositionService.getAllAdvertisedPositions(company);
		model.addAttribute("advertisedPositions", advertisedPositions);*/
		return "company/available_positions";
	}
	
	@RequestMapping("/company/create_position")
	public String createNewPosition(Model model) {
		/*User user = userService.authenticateAndGetUser();
		Company company = companyService.getCompany(user);
		TraineeshipPosition newTraineeshipPosition = traineeshipPositionService.createNewTraineeshipPosition(company);
		model.addAttribute("position", newTraineeshipPosition);*/
		return "company/create_position";
	}
	
	@PostMapping("/company/create_new_position")
	public String saveNewPosition(@ModelAttribute("position") TraineeshipPosition traineeshipPosition) {
		//traineeshipPositionService.savePosition(traineeshipPosition);
		return "company/available_positions";
	}
	
	@GetMapping("/reject")
	public String removePosition(@RequestParam("positionId") Integer positionId) {
		/*TraineeshipPosition position = traineeshipPositionService.getTraineeshipPosition(positionId);
		traineeshipPositionService.removePosition(position);*/
	    return "redirect:/company/available_positions";
	}
}
