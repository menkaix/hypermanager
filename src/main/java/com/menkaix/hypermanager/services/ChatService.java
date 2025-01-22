package com.menkaix.hypermanager.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menkaix.hypermanager.models.LLMResponseDTO;
import com.menkaix.hypermanager.models.PromptQueryDTO;
import okhttp3.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

        // Call the microservice with OKhttp

        try {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.get("application/json");
            String json = gson.toJson(promptQueryDTO);
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(serviceURL)
                    .method("POST", body)
                    .addHeader("x-api-key", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.code() == 200) {
                String responseBody = response.body().string();
                LLMResponseDTO ans = gson.fromJson(responseBody, LLMResponseDTO.class);
                return ans;
            } else {
                LLMResponseDTO errans = new LLMResponseDTO();
                errans.setError("Sorry, API returned error : " + response.code() + " < " + json);

                return errans;
            }

        } catch (IOException e) {
            logger.error("IOException in ChatService: " + e.getMessage());

            LLMResponseDTO errans = new LLMResponseDTO();
            errans.setError("IOException in ChatService: " + e.getMessage());

            return errans;
        }

    }
}
