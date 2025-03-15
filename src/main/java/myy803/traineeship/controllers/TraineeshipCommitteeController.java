package myy803.traineeship.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TraineeshipCommitteeController {
	
	@RequestMapping("/trainee_committee/dashboard")
	public String getStudentMainMenu(){
    	return "trainee_committee/dashboard";
    }
}
