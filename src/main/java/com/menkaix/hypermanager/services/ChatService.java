package com.menkaix.hypermanager.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menkaix.hypermanager.models.LLMResponseDTO;
import com.menkaix.hypermanager.models.PromptQueryDTO;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    Logger logger = org.slf4j.LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private Environment env;

    public LLMResponseDTO discussion(PromptQueryDTO promptQueryDTO) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String baseURL = env.getProperty("microservices.assist.url");
        String serviceURL = baseURL + "/discuss";
        String apiKey = env.getProperty("microservices.apikey");

        // Call the microservice with Unirest

        try {

            String json = new String(gson.toJson(promptQueryDTO).getBytes(), "UTF-8");
            HttpResponse<JsonNode> response = Unirest.post(serviceURL)
                    .header("x-api-key", apiKey)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(json)
                    .asJson();

            if (response.getStatus() == 200) {
                String responseBody = response.getBody().toString();
                LLMResponseDTO ans = gson.fromJson(responseBody, LLMResponseDTO.class);
                return ans;
            } else {
                LLMResponseDTO errans = new LLMResponseDTO();
                errans.setError("Sorry, API returned error : " + response.getStatus() + " < " + json);

                return errans;
            }

        } catch (Exception e) {
            logger.error("Exception in ChatService: " + e.getMessage());

            LLMResponseDTO errans = new LLMResponseDTO();
            errans.setError("Exception in ChatService: " + e.getMessage());

            return errans;
        }

    }
}
