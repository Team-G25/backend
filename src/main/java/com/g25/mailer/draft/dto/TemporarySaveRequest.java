package com.g25.mailer.draft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 임시메일 저장 요청 DTO
 *
 * - content 필드는 기존에 단순 String으로 받던 방식에서
 * - ContentObj (subject, body, attachments를 포함한 객체)로 변경
 *
 *
 * - 반드시 content를 객체 형태로 전송해야 합니다.
 */
@Getter
@Setter
@Schema(description = "임시메일 저장 요청 DTO")
public class TemporarySaveRequest {
    @NotNull
    @Schema(description = "사용자 이메일", example = "user@example.com", required = true)
    private String email;


    @NotNull
    @Schema(
            description = "저장할 메일 내용 객체 (기존 String에서 ContentObj로 변경됨)",
            required = true
    )
    private ContentObj content;

}
