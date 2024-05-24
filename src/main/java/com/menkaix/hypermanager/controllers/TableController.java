package com.menkaix.hypermanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.menkaix.hypermanager.models.FullProjectDTO;
import com.menkaix.hypermanager.services.ProjectService;

@Controller
public class TableController {
	
	
	@Autowired
	private ProjectService projectService ;
	
	
	@GetMapping("table/{project}")
	public String table(@PathVariable("project") String project,  Model model) {
		
		FullProjectDTO tree = projectService.getTree(project);
		
		model.addAttribute("tree", tree);
        model.addAttribute("project", project);
		
		
		return "table" ;
	}

}
