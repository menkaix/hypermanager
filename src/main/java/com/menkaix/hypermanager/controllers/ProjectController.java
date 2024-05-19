package com.menkaix.hypermanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.menkaix.hypermanager.services.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService ;
	
	@GetMapping("/infos/{project}")
    public String home(@PathVariable("project") String project, Model model) {
       
		String tree = projectService.getTree(project);
		
        model.addAttribute("tree", tree);
        model.addAttribute("project", project);
        
        return "project_infos";
    }
}