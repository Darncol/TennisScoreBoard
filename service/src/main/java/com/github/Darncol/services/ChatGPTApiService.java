package com.github.Darncol.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ChatGPTApiService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ChatGPTApiService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean checkForInappropriateContent(String text) throws Exception {

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o",
                "messages", new Object[] {
                        Map.of("role", "system", "content", "Определи, содержит ли следующее предложение нецензурную лексику, сексуальный контекст, оскорбительные слова или слова типа 'гей', 'секс' 'дура'. Ответь только true или false."),
                        Map.of("role", "user", "content", text)
                },
                "max_tokens", 5,
                "temperature", 0.0
        );

        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to call API: " + response.body());
        }

        Map<?, ?> responseBody = objectMapper.readValue(response.body(), Map.class);
        Map<?, ?> choice = (Map<?, ?>) ((java.util.List<?>) responseBody.get("choices")).get(0);
        Map<?, ?> message = (Map<?, ?>) choice.get("message");
        String result = (String) message.get("content");

        return Boolean.parseBoolean(result.trim());
    }
}




