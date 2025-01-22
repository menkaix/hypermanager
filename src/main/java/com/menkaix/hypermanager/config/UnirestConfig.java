package com.menkaix.hypermanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.annotation.PostConstruct;
import kong.unirest.core.Unirest;
import kong.unirest.core.ObjectMapper;

@Configuration
public class UnirestConfig {

    // public UnirestConfig() {
    //     // configureUnirestObjectMapper();
    // }

    // @PostConstruct
    // public void configureUnirestObjectMapper() {
    //     Gson gson = gson();
    //     Unirest.config().setObjectMapper(new ObjectMapper() {
    //         @Override
    //         public <T> T readValue(String value, Class<T> valueType) {
    //             return gson.fromJson(value, valueType);
    //         }

    //         @Override
    //         public String writeValue(Object value) {
    //             return gson.toJson(value);
    //         }
    //     });
    // }

    // public Gson gson() {
    //     return new GsonBuilder().setPrettyPrinting().create();
    // }

}
