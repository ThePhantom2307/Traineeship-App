package myy803.traineeship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import myy803.traineeship.model.User;
import myy803.traineeship.services.IntUserService;

@Controller
public class AuthController {
	@Autowired
	IntUserService userService;
	
	@RequestMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "auth/register";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute("user") User user, Model model) {
		if (userService.isUserPresent(user)) {
			model.addAttribute("errorMessage", "This username already exists!");
			return "redirect:/register?error=true";
		}
		
		userService.saveUser(user);
		model.addAttribute("successMessage", "User registered successfully!");
		
		return "auth/login";
	}
}
