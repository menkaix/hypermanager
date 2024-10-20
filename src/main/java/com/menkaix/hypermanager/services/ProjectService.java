package com.menkaix.hypermanager.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.menkaix.hypermanager.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

//import reactor.core.publisher.Mono;

@Service
public class ProjectService {

	static Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private Environment env;

	@Autowired
	private GoogleCloudAuthService auth;

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

		Unirest.setTimeouts(0, 0);
		try {
			String stringUrl = env.getProperty("microservices.backlog.url")+"/project-command/"+ project +"/tree";
			HttpResponse<String> response = Unirest.get(stringUrl)
					.header("x-api-key", env.getProperty("microservices.apikey"))
					.asString();

			if(response.getStatus() < 200 || response.getStatus()>=300){
				logger.error("fetching project tree : status "+response.getStatus());
				return null ;
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			FullProjectDTO objAns = gson.fromJson(response.getBody(), FullProjectDTO.class);

			return distributeSpan(objAns);


		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}

		//return null;
	}

	public List<Project> listProject() {


		Unirest.setTimeouts(0, 0);
		try {

			String stringUrl = env.getProperty("microservices.backlog.url")+"/project-command/all";

			HttpResponse<String> response = Unirest.get(stringUrl)
					.header("x-api-key", env.getProperty("microservices.apikey"))
					.asString();

			//logger.info("URL:'"+env.getProperty("microservices.backlog.url")+"'");
			//logger.info("API_KEY:'"+env.getProperty("microservices.apikey")+"'");
			//logger.info("status "+response.getStatus());
			//logger.info(response.getBody());


			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			try{

				if(response.getStatus() < 200 || response.getStatus()>=300){
					logger.error("fetching project list : status "+response.getStatus());
					return Collections.EMPTY_LIST ;
				}


				Project[] projects = gson.fromJson(response.getBody(), Project[].class);

				ArrayList<Project> listAns = new ArrayList<Project>();
				
				for (Project project : projects) {
					listAns.add(project);
				}
				return listAns;
			}
			catch(Exception e){
				logger.error(e.getMessage()+" "+response.getBody());
				
			}
			
			

		} catch (UnirestException e) {
			
			logger.error(e.getMessage());

		}

		return null;
	}


	//TODO new specs
	public void ingest(String project, String prompt) {

		// try {
		// client().post().uri("ingest-story/{project}", project)
		// .accept(MediaType.TEXT_PLAIN,
		// MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
		// .bodyValue("{\"data\":\"" + prompt +
		// "\"}").retrieve().bodyToMono(String.class).block();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public List<ActorDTO> getActors(String projectCode) {

		// try {

		// String ans = client().get().uri("project-command/{projectCode}/list-actors",
		// projectCode).retrieve()
		// .bodyToMono(String.class).block();

		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// ActorDTO[] actors = gson.fromJson(ans, ActorDTO[].class);

		// ArrayList<ActorDTO> listAns = new ArrayList<ActorDTO>();

		// for (ActorDTO project : actors) {
		// listAns.add(project);
		// }

		// return listAns;
		// } catch (IOException e) {
		// logger.error(e.getMessage());
		// return null;
		// }

		return null;

	}

	public List<FeatureTypeDTO> getFeaturetypes() {

		String stringUrl = env.getProperty("microservices.backlog.url")+"/featuretypes";

		HttpResponse<String> response = null;
		try {
			response = Unirest.get(stringUrl)
					.header("x-api-key", env.getProperty("microservices.apikey"))
					.asString();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			FeatureTypeDTO[] types = gson.fromJson(response.getBody(), FeatureTypeDTO[].class);

			ArrayList<FeatureTypeDTO> listAns = new ArrayList<FeatureTypeDTO>();

			for (FeatureTypeDTO type : types) {
				listAns.add(type);
			}

			return listAns;

		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}

	}



	public List<FeatureTreeDTO> featureTree(String project) {

		String stringUrl = env.getProperty("microservices.backlog.url")+"project-command/"+project+"/feature-tree";

		HttpResponse<String> response = null;

		try {
			response = Unirest.get(stringUrl)
					.header("x-api-key", env.getProperty("microservices.apikey"))
					.asString();

			Gson gson = new GsonBuilder().setPrettyPrinting().create() ;

			List<FeatureTreeDTO> tree = gson.fromJson(response.getBody(), List.class) ;

			return tree ;

		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}

	}

	public String featureTreeString(String project) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		List<FeatureTreeDTO> tree = featureTree(project);

		return gson.toJson(tree);

	}

	public void organizeFeatureHyerachy(String parentID, String childID) {

		// try {
		// String apiAns =
		// client().post().uri("/feature-command/{parent}/adopt/{child}", parentID,
		// childID).retrieve()
		// .bodyToMono(String.class).block();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public int create(Project project) {

		//

		// try {
		// String apiAns = client().post().uri("/projects")
		// .contentType(MediaType.APPLICATION_JSON)
		// .bodyValue(project)
		// .retrieve()
		// .bodyToMono(String.class).block();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String url = env.getProperty("microservices.backlog.url")+"/projects";

		ProjectCreationDTO tProject = new ProjectCreationDTO() ;
		tProject.setCode(project.getCode());
		tProject.setName(project.getName());
		tProject.setClientName(project.getClientName());
		tProject.setDescription(project.getDescription());

		Unirest.setTimeouts(0, 0);
		try {
			HttpResponse<String> response = Unirest.post(url)
					.header("x-api-key",  env.getProperty("microservices.apikey"))
					.header("Content-Type", "application/json")
					.body(tProject)
					.asString();

			logger.info("create project: "+response.getStatus());
			logger.info(gson.toJson(tProject));

			return response.getStatus() ;

		} catch (UnirestException e) {
			throw new RuntimeException(e);
		}

		//return 500 ;
	}

}
