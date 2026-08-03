package com.tanmay.devpulse.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanmay.devpulse.dto.AiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class GeminiService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent}")
    private String apiUrl;



    public GeminiService(RestClient restClient,
                         ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public AiResponse generateResponse(String prompt) {
        System.out.println("================================");
        System.out.println("API URL : " + apiUrl);
        System.out.println("API KEY : " + apiKey.substring(0, 8) + "...");
        System.out.println("================================");
        try {

            Map<String, Object> requestBody = Map.of(
                    "contents", new Object[]{
                            Map.of(
                                    "parts", new Object[]{
                                            Map.of("text", prompt)
                                    }
                            )
                    }
            );

            String response = restClient.post()
                    .uri("https://generativelanguage.googleapis.com/v1/models/gemini-2.0-flash:generateContent?key=" + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            JsonNode root = objectMapper.readTree(response);

            String text = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            return new AiResponse(text);

        } catch (Exception e) {

            return new AiResponse(
                    "Gemini API Error: " + e.getMessage()
            );
        }
    }
}