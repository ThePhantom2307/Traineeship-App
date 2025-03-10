package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeship.services.IUserService;

@Controller
public class CompanyController {
	@Autowired
	IUserService userService;
	
	@RequestMapping("/company/dashboard")
	public String getStudentMainMenu(){
	       
    	return "/company/dashboard";
    }
}
