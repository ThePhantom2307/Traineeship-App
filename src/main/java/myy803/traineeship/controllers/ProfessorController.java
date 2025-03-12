package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeship.services.IntUserService;

@Controller
public class ProfessorController {
	@Autowired
	IntUserService userService;
	
	@RequestMapping("/professor/dashboard")
	public String getStudentMainMenu(){
	       
    	return "/professor/dashboard";
    }
}
