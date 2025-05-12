package com.g25.mailer.draft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "메일 내용 객체 (발신자 주소 추가, 수신자 주소 추가) 입니다. 첨부파일은 불가합니다")
public class ContentObj {

    @Schema(description = "수신자 이메일", example = "수신자이메일주소")
    private String receiverId;

    @Schema(description = "발신자 이메일", example = "발신자@mailergo.io.kr", required = true)
    private String senderId;

    @Schema(description = "메일 제목", example = "이메일 제목", required = true)
    private String subject;

    @Schema(description = "메일 내용", example = "이메일 본문 내용", required = true)
    private String body;

}
