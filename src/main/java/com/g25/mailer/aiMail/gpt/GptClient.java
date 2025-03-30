package com.g25.mailer.aiMail.gpt;

import com.g25.mailer.aiMail.dto.GptResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GptClient {

    private final RestTemplate restTemplate;

    @Value("${openai.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    public GptClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendMessage(String systemMessage, String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Authorization 헤더는 GptConfig에서 이미 처리

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);

        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", systemMessage),
                Map.of("role", "user", "content", userMessage)
        );

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<GptResponse> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    GptResponse.class
            );

            String content = response.getBody()
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

            return content;

        } catch (HttpStatusCodeException e) {
            log.error("GPT API 오류 - 응답 바디: {}", e.getResponseBodyAsString());
            throw new RuntimeException("OpenAI 호출 실패: " + e.getStatusCode());
        } catch (Exception e) {
            log.error("GPT 응답 처리 중 예외 발생", e);
            throw new RuntimeException("GPT 응답 처리 중 예외 발생", e);
        }
    }
}
