package com.menkaix.hypermanager.models;

import java.util.Date;

public abstract class AbstractEntity {
	
	public String name;
	public Date creationDate=new Date() ;
	public Date lastUpdateDate ;

}
