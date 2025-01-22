package com.menkaix.hypermanager.controllers;

import com.menkaix.hypermanager.models.ConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private Environment env ;

    @GetMapping
    public String home(Model model) {

        ConfigDTO config = new ConfigDTO(env.getProperty("baseURL"), env.getProperty("apiKey"));


        model.addAttribute("config", config);

        return "index";
    }
}