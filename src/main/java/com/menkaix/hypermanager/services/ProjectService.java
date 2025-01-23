package com.menkaix.hypermanager.services;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.menkaix.hypermanager.models.project.ProjectHomeWrapperDTO;
import com.menkaix.hypermanager.models.project.ProjectSmallDTO;

public class ProjectService {

    public ProjectHomeWrapperDTO getProjectsHome() {
        // TODO replace this place Holder

        ProjectHomeWrapperDTO projects = new ProjectHomeWrapperDTO();

        ArrayList<ProjectSmallDTO> projectArray = new ArrayList<ProjectSmallDTO>();

        ProjectSmallDTO project1 = new ProjectSmallDTO("1", "Project 1", "P1");
        ProjectSmallDTO project2 = new ProjectSmallDTO("2", "Project 2", "P2");
        ProjectSmallDTO project3 = new ProjectSmallDTO("3", "Project 3", "P3");

        projectArray.add(project1);
        projectArray.add(project2);
        projectArray.add(project3);

        projects.setProjects(projectArray);

        return projects;
    }

}
