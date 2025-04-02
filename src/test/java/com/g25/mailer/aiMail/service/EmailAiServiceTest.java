package com.g25.mailer.aiMail.service;

import com.g25.mailer.aiMail.gpt.GptClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailAiServiceTest {

    @Mock
    private GptClient gptClient;

    @InjectMocks
    private EmailAiService emailAiService;

    @Test
    @DisplayName("출결문의 메일생성")
    void 출결_메일_자동생성() {
        // given
        String prompt = "지각 사유에 대해 교수님께 정중하게 출결 문의 메일을 작성해줘";
        String expected = "교수님, 수업에 지각한 점 사과드리며 출석 인정 가능 여부를 여쭙습니다.";
        when(gptClient.sendMessage(anyString(), eq(prompt))).thenReturn(expected);

        // when
        String result = emailAiService.generateEmailContent(prompt);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("출결요청 메일 교정")
    void 출결_요청_문장_교정() {
        // given
        String input = "교수님 지각했는데 출석 처리 해주실 수 있나요";
        String expected = "교수님, 오늘 지각하여 출석 여부에 대해 여쭤보고 싶습니다.";
        when(gptClient.sendMessage(anyString(), eq(input))).thenReturn(expected);

        // when
        String result = emailAiService.refineEmailContent(input);

        // then
        assertEquals(expected, result);
    }
}
