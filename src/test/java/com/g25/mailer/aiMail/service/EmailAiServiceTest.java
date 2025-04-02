package com.g25.mailer.aiMail.service;

import com.g25.mailer.aiMail.gpt.GptClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailAiServiceTest {

    @Mock
    private GptClient gptClient;

    @InjectMocks
    private EmailAiService emailAiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("출결 관련 메일 자동생성")
    void generateEmailContent_Attendance() {

        String prompt = "지각 사유에 대해 교수님께 정중하게 출결 문의 메일을 작성해줘";
        String expected = "교수님, 수업에 지각한 점 사과드리며 출석 인정 가능 여부를 여쭙습니다.";
        when(gptClient.sendMessage(anyString(), eq(prompt))).thenReturn(expected);

        String result = emailAiService.generateEmailContent(prompt);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("출결 관련 메일 교정")
    void refineEmailContent_refines() {

        String content = "교수님 지각했는데 출석 처리 해주실 수 있나요";
        String expected = "교수님, 오늘 지각하여 출석 여부에 대해 여쭤보고 싶습니다.";
        when(gptClient.sendMessage(anyString(), eq(content))).thenReturn(expected);

        String result = emailAiService.refineEmailContent(content);
        assertEquals(expected, result);
    }
}
