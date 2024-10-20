package com.menkaix.hypermanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.menkaix.hypermanager.models.FullProjectDTO;
import com.menkaix.hypermanager.models.Project;
import com.menkaix.hypermanager.models.Prompt;
import com.menkaix.hypermanager.services.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService ;
	
	@GetMapping("/infos/{project}")
    public String toViewProjectInfos(@PathVariable("project") String project, Model model) {
       
		FullProjectDTO tree = projectService.getTree(project);
		
		Prompt prompt = new Prompt() ;
		prompt.project = project ;
		
		model.addAttribute("prompt",prompt);
        model.addAttribute("tree", tree);
        model.addAttribute("project", project);
        
        return "project_infos";
    }

	@GetMapping("/all")
    public String toViewProjectInfos(Model model) {
       
		List<Project> projects = projectService.listProject() ;
		
		model.addAttribute("projects", projects);
		model.addAttribute("project", new Project()) ;
        
        return "index-projects";
    }
	
	@PostMapping("/ingest")
	public String actionIngestInProject(@ModelAttribute Prompt prompt, Model model) {
		
		//model.addAttribute("prompt",prompt);
		
		projectService.ingest(prompt.project, prompt.prompt) ;
		
		
		return "redirect:/project/infos/"+prompt.project ;
	}
	
	@PostMapping("/new")
	public String actionCreateProject(@ModelAttribute Project project, Model model) {
		
		//model.addAttribute("prompt",prompt);
		
		projectService.create(project) ;
		
		
		return "redirect:/" ;
	}
}