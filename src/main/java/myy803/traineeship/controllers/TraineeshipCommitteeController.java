package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeship.services.IntUserService;

@Controller
public class TraineeshipCommitteeController {
	@Autowired
	IntUserService userService;
	
	@RequestMapping("/trainee_commitee/dashboard")
	public String getStudentMainMenu(){
    	return "/trainee_commitee/dashboard";
    }
}
