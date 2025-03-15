package myy803.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.traineeship.model.Company;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;
import myy803.traineeship.services.IntCompanyService;
import myy803.traineeship.services.IntStudentService;
import myy803.traineeship.services.IntUserService;

@Controller
public class CompanyController {
	@Autowired
	IntUserService userService;
	
	@Autowired
	private IntCompanyService companyService;
	
	@RequestMapping("/company/dashboard")
	public String getStudentMainMenu(){
    	return "company/dashboard";
    }
	
	@RequestMapping("/company/profile")
	public String getProfile(Model model) {
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
}
