package com.menkaix.hypermanager.models;

import java.util.ArrayList;
import java.util.List;

public class FullActorDTO {

    public String id;
    public String name ;
    public String projectName ;
    public String description ;
    public String type ;

    public List<FullStoryDTO> stories = new ArrayList<>() ;
}
