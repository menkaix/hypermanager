package com.menkaix.hypermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.menkaix.hypermanager.config",
		"com.menkaix.hypermanager.controllers",
		"com.menkaix.hypermanager.services"
})
public class HypermanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HypermanagerApplication.class, args);
	}

}
