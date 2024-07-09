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
import com.menkaix.hypermanager.models.FeatureTreeDTO;
import com.menkaix.hypermanager.models.FeatureTypeDTO;
import com.menkaix.hypermanager.models.FullActorDTO;
import com.menkaix.hypermanager.models.FullFeatureDTO;
import com.menkaix.hypermanager.models.FullProjectDTO;
import com.menkaix.hypermanager.models.FullStoryDTO;
import com.menkaix.hypermanager.models.FullTaskDTO;
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

	private FullProjectDTO distributeSpan(FullProjectDTO objAns) {

		for (FullActorDTO actor : objAns.actors) {

			actor.span = 0;

			for (FullStoryDTO story : actor.stories) {

				story.span = 0;

				for (FullFeatureDTO feature : story.features) {
					feature.span = 0;

					if (feature.tasks.size() == 0) {
						FullTaskDTO placeHolder = new FullTaskDTO();
						placeHolder.title = "(empty)";
						feature.tasks.add(placeHolder);
					}

					for (FullTaskDTO task : feature.tasks) {
						actor.span++;
						story.span++;
						feature.span++;
					}

				}

			}

		}

		return objAns;
	}

	public FullProjectDTO getTree(String project) {

		try {

			String ans = client().get().uri("project-command/{project}/tree", project).retrieve()
					.bodyToMono(String.class).block();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			FullProjectDTO objAns = gson.fromJson(ans, FullProjectDTO.class);

			return distributeSpan(objAns);

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

			String ans = client().get().uri("project-command/{projectCode}/list-actors", projectCode).retrieve()
					.bodyToMono(String.class).block();

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

	public List<FeatureTypeDTO> getFeaturetypes() {

		try {

			String ans = client().get().uri("/featuretypes").retrieve().bodyToMono(String.class).block();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			FeatureTypeDTO[] types = gson.fromJson(ans, FeatureTypeDTO[].class);

			ArrayList<FeatureTypeDTO> listAns = new ArrayList<FeatureTypeDTO>();

			for (FeatureTypeDTO type : types) {
				listAns.add(type);
			}

			return listAns;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}

	}
	
	public String featureTreeString(String project) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create() ;
		
		List<FeatureTreeDTO> tree = featureTree(project) ;
		
		return gson.toJson(tree);
		
	}

	public List<FeatureTreeDTO> featureTree(String project) {

		List<FeatureTreeDTO> ans;

		try {
			String apiAns = client().get().uri("project-command/{project}/feature-tree", project).retrieve()
					.bodyToMono(String.class).block();
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create() ;

			List<FeatureTreeDTO> tree = gson.fromJson(apiAns, List.class) ;
			
			return tree ;

		} catch (IOException e) {
			logger.error("getting feature tree :" + e.getMessage());
		}

		return null;
	}

}
