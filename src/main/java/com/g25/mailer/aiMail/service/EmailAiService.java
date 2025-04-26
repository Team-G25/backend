package com.g25.mailer.aiMail.service;

import com.g25.mailer.aiMail.dto.GptResponse;
import com.g25.mailer.aiMail.gpt.GptClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailAiService {

    private final GptClient gptClient;

    @Value("${gpt.api-key}")
    private String apiKey;

    // 자동생성
    public String generateEmailContent(String prompt) {
        return sendGptRequest(prompt);
    }

    // 문장 다듬기
    public String refineEmailContent(String content) {
        return sendGptRequest(content);
    }

    private String sendGptRequest(String userContent) {
        Map<String, Object> request = Map.of(
                "model", "gpt-3.5-turbo",
                "temperature", 0.7,
                "messages", List.of(
                        Map.of("role", "system", "content", """
                        당신은 이메일 문장을 부드럽고 자연스럽게 다듬는 AI입니다.
                        원래 의미를 변경하지 말고, 문법, 어투만 부드럽게 고쳐주세요.
                        절대로 추가 설명이나 답변은 하지 말고,
                        수정된 문장만 그대로 반환하세요.
                        """),
                        Map.of("role", "user", "content", userContent)
                )
        );

        GptResponse response = gptClient.sendMessage(request, "Bearer " + apiKey);
        return response.getChoices().get(0).getMessage().getContent();
    }

}
