package com.g25.mailer.draft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "메일 내용 객체")
public class ContentObj {
    @Schema(description = "메일 제목", example = "테스트 제목", required = true)
    private String subject;

    @Schema(description = "메일 본문 내용", example = "테스트 본문 내용입니다.", required = true)
    private String body;

    @Schema(description = "첨부파일 이름 목록", example = "[\"첨부1.pdf\", \"첨부2.png\"]")
    private List<String> attachments;
}
