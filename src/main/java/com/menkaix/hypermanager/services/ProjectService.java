package com.menkaix.hypermanager.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menkaix.hypermanager.models.ActorDTO;
import com.menkaix.hypermanager.models.FullProjectDTO;
import com.menkaix.hypermanager.models.Project;

import reactor.core.publisher.Mono;

@Service
public class ProjectService {

	static Logger logger = LoggerFactory.getLogger(ProjectService.class);

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

	public FullProjectDTO getTree(String project) {

		try {

			String ans = client().get().uri("project-command/{project}/tree", project).retrieve()
					.bodyToMono(String.class).block();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			FullProjectDTO objAns = gson.fromJson(ans, FullProjectDTO.class);

			return objAns;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<Project> listProject() {

		String ans;

		try {
			ans = client().get().uri("project-command/all").retrieve().bodyToMono(String.class).block();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			Project[] projects = gson.fromJson(ans, Project[].class);

			ArrayList<Project> listAns = new ArrayList<Project>();

			for (Project project : projects) {
				listAns.add(project);
			}

			return listAns;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void ingest(String project, String prompt) {

		try {
			client().post().uri("ingest-story/{project}", project)
					.accept(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.bodyValue("{\"data\":\"" + prompt + "\"}").retrieve().bodyToMono(String.class).block();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<ActorDTO> getActors(String projectCode) {

		try {

			String ans = client().get().uri("project-command/{projectCode}/list-actors", projectCode).retrieve().bodyToMono(String.class).block();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			ActorDTO[] actors = gson.fromJson(ans, ActorDTO[].class);

			ArrayList<ActorDTO> listAns = new ArrayList<ActorDTO>();

			for (ActorDTO project : actors) {
				listAns.add(project);
			}

			return listAns;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}

	}

}
