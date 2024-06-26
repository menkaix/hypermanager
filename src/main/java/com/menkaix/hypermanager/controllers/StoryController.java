package com.menkaix.hypermanager.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.menkaix.hypermanager.models.ActorDTO;
import com.menkaix.hypermanager.models.FeatureDTO;
import com.menkaix.hypermanager.models.FeatureTypeDTO;
import com.menkaix.hypermanager.models.FullStoryDTO;
import com.menkaix.hypermanager.models.Prompt;
import com.menkaix.hypermanager.models.StoryDTO;
import com.menkaix.hypermanager.services.ProjectService;
import com.menkaix.hypermanager.services.StoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/story")
public class StoryController {
	
	@Autowired
	private ProjectService projectService ;
	
	@Autowired
	private StoryService storyService ;
	
	@GetMapping("/details/{storyID}")
	public String getdetaims(@PathVariable("storyID") String storyID, Model model) {
		
		FullStoryDTO storyDTO = storyService.getTree(storyID) ;
		
		List<ActorDTO> actors = projectService.getActors(storyDTO.projectCode) ;
		
		List<FeatureTypeDTO> types = projectService.getFeaturetypes() ;
		
		FeatureDTO newFeature = new FeatureDTO() ;
		newFeature.storyId = storyID ;
		
		model.addAttribute("story", storyDTO);
		model.addAttribute("actors", actors);
		model.addAttribute("newFeature", newFeature);
		model.addAttribute("types", types);
		
		return "story_details";
	}
	
	@PostMapping("/update")
	public String postMethodName(@ModelAttribute FullStoryDTO storyDTO, Model model) {
		
		storyService.update(storyDTO) ;
		
		return "redirect:/story/details/"+storyDTO.id;
	}
	
	@PostMapping("/add_feature")
	public String postMethodName(@ModelAttribute FeatureDTO newFeature, Model model) {
		
		storyService.addFeature(newFeature) ;
		
		return "redirect:/story/details/"+newFeature.storyId;
	}
	
	

}
