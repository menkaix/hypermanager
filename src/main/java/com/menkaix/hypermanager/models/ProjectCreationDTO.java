package com.menkaix.hypermanager.models;

public class ProjectCreationDTO {

    public String id;

    public String name ;

    public String code ;
    public String clientName ;

    public String description ;

    public ProjectCreationDTO() {

    }

    public ProjectCreationDTO(String name) {
        this.name = name ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {

        this.name = name ;

    }
}
