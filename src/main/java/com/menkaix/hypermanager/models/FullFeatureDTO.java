package com.menkaix.hypermanager.models;

import java.util.ArrayList;

public class FullFeatureDTO {

    public String id;
    public String name ;
    public String description;
    public String type;
    public String parentID;
    
    public int span ;
    
    public ArrayList<FullTaskDTO> tasks = new ArrayList() ;
}
