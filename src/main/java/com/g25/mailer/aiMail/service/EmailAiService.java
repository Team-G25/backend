package com.g25.mailer.aiMail.service;

import com.g25.mailer.aiMail.gpt.GptClient;
import org.springframework.stereotype.Service;

@Service
public class EmailAiService {

    private GptClient gptClient;

    public String refineEmailContent(String content) {
        String systemMessage = "다음 이메일 내용을 자연스럽고 정중하게 정제해줘. 문장만 출력해.";
        return gptClient.sendMessage(systemMessage, content);
    }

    public String generateEmailContent(String prompt) {
        String systemMessage = "다음 맥락에 대한 내용에 적합한 메일을 작성해줘. 문장만 출력해.";
        return gptClient.sendMessage(systemMessage, prompt);
    }


}
