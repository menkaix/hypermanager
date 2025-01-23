package com.menkaix.hypermanager.models.project;

import java.util.List;

public class ProjectHomeWrapperDTO {

    private List<ProjectSmallDTO> projects;

    public List<ProjectSmallDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectSmallDTO> projects) {
        this.projects = projects;
    }

    public ProjectHomeWrapperDTO() {
    }

}
