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
import com.menkaix.hypermanager.models.FullStoryDTO;
import com.menkaix.hypermanager.models.Prompt;
import com.menkaix.hypermanager.models.StoryDTO;
import com.menkaix.hypermanager.services.StoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/story")
public class StoryController {
	
	@Autowired
	private StoryService storyService ;
	
	@GetMapping("/details/{storyID}")
	public String getdetaims(@PathVariable("storyID") String storyID, Model model) {
		
		FullStoryDTO storyDTO = storyService.getTree(storyID) ;
		
		List<ActorDTO> actors = new ArrayList<ActorDTO>() ;
		
		ActorDTO dto1 = new ActorDTO() ;
		ActorDTO dto2 = new ActorDTO() ;
		ActorDTO dto3 = new ActorDTO() ;
		
		dto1.id = "1" ; dto1.name = "alice" ;
		dto2.id = "2" ; dto2.name = "bob" ;
		dto3.id = "3" ; dto3.name = "dispatcher" ;
		
		actors.add(dto1);
		actors.add(dto2);
		actors.add(dto3);
		
		model.addAttribute("story", storyDTO);
		model.addAttribute("actors", actors);
		
		return "story_details";
	}
	
	@PostMapping("/update")
	public String postMethodName(@ModelAttribute FullStoryDTO storyDTO, Model model) {
		//TODO: process POST request
		
		return "redirect:/story/details/"+storyDTO.id;
	}
	
	

}
