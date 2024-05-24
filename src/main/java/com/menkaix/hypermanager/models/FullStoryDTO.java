package com.menkaix.hypermanager.models;

import java.util.ArrayList;
import java.util.List;

public class FullStoryDTO {

    public String id;

    public String projectCode ;

    public String action ;

	public String actorName;

	public String objective ;

	public String scenario ;

	public int span ;
	
	public List<FullFeatureDTO> features = new ArrayList<>() ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public List<FullFeatureDTO> getFeatures() {
		return features;
	}

	public void setFeatures(List<FullFeatureDTO> features) {
		this.features = features;
	}

}
