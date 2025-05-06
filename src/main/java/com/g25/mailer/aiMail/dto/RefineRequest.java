package com.g25.mailer.aiMail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AI 메일 문법·어투 교정 요청 DTO")
public class RefineRequest {
    @Schema(description = "AI가 교정할 메일 원본 내용", example = "교수님 안녕하세요, 과제 제출이 늦어 죄송합니다.")
    private String content;
}ㅎ
