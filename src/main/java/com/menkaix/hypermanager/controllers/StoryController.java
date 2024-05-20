package com.menkaix.hypermanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.menkaix.hypermanager.models.FullStoryDTO;
import com.menkaix.hypermanager.services.StoryService;


@Controller
@RequestMapping("/story")
public class StoryController {
	
	@Autowired
	private StoryService storyService ;
	
	@GetMapping("/details/{storyID}")
	public String getdetaims(@PathVariable("storyID") String storyID, Model model) {
		
		FullStoryDTO storyDTO = storyService.getTree(storyID) ;
		
		model.addAttribute("story", storyDTO);
		
		return "story_details";
	}
	

}
