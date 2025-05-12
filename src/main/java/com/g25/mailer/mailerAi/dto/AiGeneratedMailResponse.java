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
@Schema(description = "AI가 생성한 메일 응답 DTO")
public class AiGeneratedMailResponse {

    @Schema(description = "메일러 AI가 생성한 이메일 본문 내용", example = "메일러 AI가 생성한 메일이 뜨거나 실패하거나 둘중 하나 ")
    private String content;
}