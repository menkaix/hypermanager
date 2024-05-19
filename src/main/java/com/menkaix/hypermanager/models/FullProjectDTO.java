package com.menkaix.hypermanager.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FullProjectDTO {

    public String id;
    public String name ;
    public String code ;
    public String clientName ;

    public String description ;

    public Date creationDate ;

    public List<FullActorDTO> actors = new ArrayList<>() ;

}
