package com.menkaix.hypermanager.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menkaix.hypermanager.models.FullProjectDTO;
import com.menkaix.hypermanager.models.FullStoryDTO;

@Service
public class StoryService {
	
	static Logger logger = LoggerFactory.getLogger(StoryService.class);

	@Autowired
	private Environment env;

	@Autowired
	private GoogleCloudAuthService auth;
	
	private WebClient client() throws IOException {

		String url = env.getProperty("microservices.backlog.url");

		logger.info(url);

		String token = auth.getIdentityToken(url);

		WebClient client = WebClient.builder().baseUrl(url).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.build();

		return client;
	}

	

	public FullStoryDTO getTree(String storyID) {
		try {

			String ans = client().get().uri("story-command/{storyID}/tree", storyID).retrieve()
					.bodyToMono(String.class).block();

			Gson gson = new GsonBuilder().setPrettyPrinting().create() ;
			
			FullStoryDTO objAns = gson.fromJson(ans, FullStoryDTO.class);
			
			return objAns;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
