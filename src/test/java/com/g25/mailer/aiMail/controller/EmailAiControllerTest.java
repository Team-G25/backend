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
    private GptClient gptClient;

    @Test
    @DisplayName("출결 문의 메일 생성 API - 성공 응답 리턴")
    void generateEmail_returnsAttendanceMail() throws Exception {

        String prompt = "지각 사유에 대해 교수님께 정중하게 출결 문의 메일을 작성해줘";
        String generated = "교수님, 지각하여 수업에 참석하지 못한 점 양해 부탁드리며, 출석 인정 가능 여부를 여쭙고자 합니다.";

        given(gptClient.sendMessage(anyString(), eq(prompt)))
                .willReturn(generated);

        AutoGenerateRequest request = new AutoGenerateRequest(prompt);

        performPost("/ai/generate", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(generated));
    }

    @Test
    @DisplayName("출결 관련 문장 교정 API - 정제된 문장 리턴")
    void refineEmail_returnsRefinedAttendanceText() throws Exception {

        String content = "교수님 지각했는데 출석 처리 해주실 수 있나요";
        String refined = "교수님, 오늘 지각하여 출석 여부에 대해 여쭙고 싶습니다.";

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
