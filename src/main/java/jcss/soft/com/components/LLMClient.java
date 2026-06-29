package jcss.soft.com.components;

import jcss.soft.com.models.GeminiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LLMClient {
    private final WebClient webClient;

    public String run(String uri, String apiKey, String model, String input, String header) {
        try {
            String response = webClient.method(HttpMethod.POST)
                    .uri(uri)
                    .header("header", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(GeminiRequest.builder().model(model).input(input).build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("GEMINI RESPONSE: {}", response);
            return response;
        }
        catch (WebClientResponseException e) {
            log.error("Status: {}", e.getStatusCode());
            log.error("Body: {}", e.getResponseBodyAsString());
            throw e;
        }
    }
}
