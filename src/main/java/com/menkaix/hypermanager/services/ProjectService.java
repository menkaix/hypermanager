package com.menkaix.hypermanager.services;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.menkaix.hypermanager.models.project.ProjectFullDTO;
import com.menkaix.hypermanager.models.project.ProjectHomeWrapperDTO;
import com.menkaix.hypermanager.models.project.ProjectSmallDTO;

@Service
public class ProjectService {

    private static Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private Environment env;

    public ProjectHomeWrapperDTO getProjectsHome() {

        String backlogURL = env.getProperty("microservices.backlog.url");
        String apikey = env.getProperty("microservices.apikey");

        ProjectHomeWrapperDTO projects = new ProjectHomeWrapperDTO();
        ArrayList<ProjectSmallDTO> projectArray = new ArrayList<ProjectSmallDTO>();

        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest
                    .get(backlogURL + "/project-command/all")
                    .header("x-api-key", apikey)
                    .asString();

            ObjectMapper mapper = new ObjectMapper();
            List<ProjectFullDTO> projectFullList = mapper.readValue(response.getBody(),
                    new TypeReference<List<ProjectFullDTO>>() {
                    });

            for (ProjectFullDTO projectFull : projectFullList) {
                ProjectSmallDTO projectSmall = new ProjectSmallDTO(projectFull.getId(), projectFull.getName(),
                        projectFull.getCode());
                projectArray.add(projectSmall);
            }

        } catch (UnirestException | IOException e) {
            logger.error("Exception on getProjectsHome ", e);
        }

        projects.setProjects(projectArray);

        return projects;
    }

}
