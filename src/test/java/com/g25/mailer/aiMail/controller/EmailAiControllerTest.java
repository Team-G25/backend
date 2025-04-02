package com.g25.mailer.aiMail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g25.mailer.aiMail.dto.AutoGenerateRequest;
import com.g25.mailer.aiMail.dto.RefineRequest;
import com.g25.mailer.aiMail.gpt.GptClient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmailAiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GptClient gptClient; // GPT만 mock

    @Test
    @DisplayName("자동 이메일 생성 API - 성공 응답 반환")
    void generateEmail_returnsGeneratedContent() throws Exception {
        String prompt = "이력서를 바탕으로 자기소개서를 작성해줘";
        String generated = "자동 생성된 이메일입니다.";

        given(gptClient.sendMessage(anyString(), eq(prompt)))
                .willReturn(generated);

        AutoGenerateRequest request = new AutoGenerateRequest(prompt);

        performPost("/ai/generate", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(generated));
    }

    @Test
    @DisplayName("이메일 문법 교정 API - 교정된 텍스트 반환")
    void refineEmail_returnsRefinedText() throws Exception {
        String content = "안녕하세요 저는 개발자입니다";
        String refined = "안녕하세요. 저는 개발자입니다.";

        given(gptClient.sendMessage(anyString(), eq(content)))
                .willReturn(refined);

        RefineRequest request = new RefineRequest(content);

        performPost("/ai/refine", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refined").value(refined));
    }

    private org.springframework.test.web.servlet.ResultActions performPost(String url, Object body) throws Exception {
        return mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
        );
    }
}
