package com.menkaix.hypermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {
		"com.menkaix.hypermanager.controllers",
		"com.menkaix.hypermanager.configuration",
		"com.menkaix.hypermanager.services"
})
@SpringBootApplication
public class HypermanagerFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(HypermanagerFrontApplication.class, args);
	}

}
