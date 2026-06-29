package jcss.soft.com.services.impl;

import jcss.soft.com.components.LLMClient;
import jcss.soft.com.services.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    @Value("${gemini.uri}")
    private String geminiUri;

    @Value("${gemini.apikey}")
    private String geminiApiKey;

    @Value("${gemini.model}")
    private String geminiModel;

    @Value("${gemini.uri}")
    private String groqUri;

    @Value("${gemini.apikey}")
    private String groqUriApiKey;

    @Value("${gemini.model}")
    private String groqUriModel;

    private final LLMClient client;

    @Override
    public void process(String input) {
        try {

            String header = "x-goog-api-key";
            String response = client.run(geminiUri, geminiApiKey, geminiModel, input, header);
        }catch (Exception e) {
            throw e;
        }
    }


}
