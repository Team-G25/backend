package com.g25.mailer.aiMail.service;

import com.g25.mailer.aiMail.dto.GptResponse;
import com.g25.mailer.aiMail.gpt.GptClient;
import lombok.RequiredArgsConstructor;
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

    // 자동 생성
    public String generateEmailContent(String prompt) {
        return sendGptRequest(prompt);
    }

    // 문장 다듬기 (비즈니스 포맷 특화)
    public String refineEmailContent(String content) {
        return sendGptRequest(content);
    }

    private String sendGptRequest(String userContent) {
        Map<String, Object> request = Map.of(
                "model", "gpt-3.5-turbo",
                "temperature", 0.7,
                "messages", List.of(
                        Map.of("role", "system", "content", """
                            당신은 비즈니스 이메일을 자연스럽고 격식 있게 다듬는 AI입니다.
                            작성자의 원래 의도를 그대로 유지하면서, 존댓말, 문법, 어투를 교정합니다.
                            추가 설명, 정보 보충, 개인적 의견 없이, 수정된 문장만 반환하세요.
                            단정적이고 명료한 표현을 사용하세요.
                            """),
                        Map.of("role", "user", "content", userContent)
                )
        );

        GptResponse response = gptClient.sendMessage(request, "Bearer " + apiKey);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
