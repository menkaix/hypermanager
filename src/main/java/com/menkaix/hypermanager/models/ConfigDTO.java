package com.menkaix.hypermanager.models;

public class ConfigDTO {

    private String baseURL;
    private String apiKey;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ConfigDTO(String baseURL, String apiKey) {
        this.baseURL = baseURL;
        this.apiKey = apiKey;
    }

}
