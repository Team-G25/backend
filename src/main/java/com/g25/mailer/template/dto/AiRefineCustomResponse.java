package com.g25.mailer.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "AI 피드백 응답")
public class AiRefineCustomResponse {
    private String refinedContent;
}
