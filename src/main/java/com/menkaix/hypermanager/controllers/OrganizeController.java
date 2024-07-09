package com.menkaix.hypermanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.menkaix.hypermanager.services.ProjectService;

@RequestMapping("/organize")
@Controller
public class OrganizeController {
	
	@Autowired
	private ProjectService projectService ;

	@GetMapping("/features/{project}")
	public String home(@PathVariable("project") String project, Model model) {

		
		String jsonCode = projectService.featureTreeString(project);
		
		
		model.addAttribute("project", project);
		model.addAttribute("jsonCode", jsonCode) ;

		return "organize_features";
	}

}