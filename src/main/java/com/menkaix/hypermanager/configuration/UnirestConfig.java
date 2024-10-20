package com.menkaix.hypermanager.configuration;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnirestConfig {


    @PostConstruct
    public void postConstruct() {
        Unirest.setObjectMapper(new ObjectMapper() {

            private Gson gson = new GsonBuilder().setPrettyPrinting().create() ;

            public <T> T readValue(String s, Class<T> aClass) {
                try{
                    return gson.fromJson(s, aClass);
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object o) {
                try{
                    return gson.toJson(o);
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }

        });
    }
}