package com.menkaix.hypermanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.menkaix.hypermanager.models.Project;
import com.menkaix.hypermanager.services.ProjectService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private ProjectService projectService ;

	@GetMapping
    public String home(Model model/* ,
                       @AuthenticationPrincipal OAuth2User user*/) {
        
        String name = "";
        String email = "";
        //if(user != null) {
        //    name = user.getAttribute("name");
        //    email = user.getAttribute("email");
        //}
        
        //System.out.println(user)
        
        
        
        //List<Project> projects = projectService.listProject() ;
        
        //model.addAttribute("projects", projects);
        
        //model.addAttribute("project", new Project()) ;
        
        //model.addAttribute("name", name);
        //model.addAttribute("email", email);
        
        
        return "index";
    }
}