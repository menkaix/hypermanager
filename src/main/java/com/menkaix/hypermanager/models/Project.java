package com.menkaix.hypermanager.models;

public class Project  extends AbstractEntity {
	
	
	public String id;
	
	public String code ;
	public String clientName ;

	public String description ;

	public Project() {
		
	}
	
	public Project(String name) {
		this.name = name ;
	}

}
