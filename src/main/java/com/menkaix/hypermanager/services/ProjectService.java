package com.menkaix.hypermanager.services;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ProjectService {
	
	static Logger logger = LoggerFactory.getLogger(ProjectService.class) ;
	
	@Autowired
	private Environment env ;
	
	@Autowired
	private GoogleCloudAuthService auth ;
	

	public String getTree(String project) {
		
		String url = env.getProperty("microservices.backlog.url") ;
		
		logger.info(url);
		
		try {
			String token = auth.getIdentityToken(url) ;
			
			WebClient client = WebClient.builder()
					  .baseUrl(url)
					  .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer "+token) 					  
					  .build();
			
			String ans = client.get()
			.uri("project-command/{project}/tree", project)
			.retrieve()
			.bodyToMono(String.class)
			.block();
			
			return ans ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		return "error getting tree";
	}

}
