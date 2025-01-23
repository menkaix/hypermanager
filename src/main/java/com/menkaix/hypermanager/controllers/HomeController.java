package com.menkaix.hypermanager.controllers;

import com.menkaix.hypermanager.models.ConfigDTO;
import com.menkaix.hypermanager.models.project.ProjectHomeWrapperDTO;
import com.menkaix.hypermanager.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private Environment env;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String home(Model model) {

        ConfigDTO config = getConfig();
        ProjectHomeWrapperDTO projects = projectService.getProjectsHome();

        model.addAttribute("config", config);
        model.addAttribute("projects", projects);

        return "index";
    }

    private ConfigDTO getConfig() {

        String baseURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        // forcing the base url to start with https:// if it starts with http://
        if (baseURL.startsWith("http://")) {
            baseURL = baseURL.replace("http://", "https://");
        }

        ConfigDTO config = new ConfigDTO(
                baseURL,
                "");

        return config;
    }
}