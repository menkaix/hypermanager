package com.menkaix.hypermanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/organize")
@Controller
public class OrganizeController {

	@GetMapping("/features/{project}")
	public String home(@PathVariable("project") String project, Model model) {

		
		String jsonCode = "Place\nholder";
		
		
		model.addAttribute("project", project);
		model.addAttribute("jsonCode", jsonCode) ;

		return "organize_features";
	}

}
