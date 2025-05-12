package com.g25.mailer.mailerAi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메일러 AI로 전달할 요청 DTO")
public class AiPromptRequest {

    @Schema(description = "메일러 AI를 사용하기 위해 사용자가 입력", example = "프로젝트 보고서 대신 보낼 메일을 작성해줘")
    private String prompt;
}
