package com.menkaix.hypermanager.controllers;

import com.menkaix.hypermanager.models.ConfigDTO;
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
    private Environment env ;

    @GetMapping
    public String home(Model model) {

        String baseURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        //forcing the base url to start with https:// if it starts with http://
        if(baseURL.startsWith("http://")){
            baseURL = baseURL.replace("http://", "https://");
        }

        ConfigDTO config = new ConfigDTO(
                baseURL,
                "");


        model.addAttribute("config", config);

        return "index";
    }
}