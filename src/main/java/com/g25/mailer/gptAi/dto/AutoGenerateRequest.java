package com.g25.mailer.gptAi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 메일 자동 생성 요청 DTO")
public class AutoGenerateRequest {
    @Schema(description = "AI에게 전달할 메일 작성 프롬프트", example = "교수님에게 과제 연장 요청 메일 작성")
    private String prompt;
}