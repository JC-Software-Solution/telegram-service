package jcss.soft.com.components;

import jcss.soft.com.dto.Content;
import jcss.soft.com.dto.GeminiResponse;
import jcss.soft.com.models.GeminiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeminiClient {

    private final WebClient webClient;

    @Value("${gemini.uri}")
    private String geminiUri;

    @Value("${gemini.apikey}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    public String run(String prompt) {
        try {
            GeminiResponse response = webClient.method(HttpMethod.POST)
                    .uri(geminiUri)
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(GeminiRequest.builder().model(model).input(prompt).build())
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block();

            return response.getSteps().stream()
                    .filter(step -> "model_output".equals(step.getType()))
                    .flatMap(step -> step.getContent().stream())
                    .filter(content -> "text".equals(content.getType()))
                    .map(Content::getText)
                    .findFirst()
                    .orElse("");
        }
        catch (WebClientResponseException e) {
            log.error("Status: {}", e.getStatusCode());
            log.error("Body: {}", e.getResponseBodyAsString());
            throw e;
        }
    }
}
