package com.example.traineeship;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Welcome to Traineeship App");
        model.addAttribute("message", "This is the main page of the application.");
        return "index";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }
}
