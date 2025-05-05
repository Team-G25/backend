package com.g25.mailer.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "공통 에러 응답 정보")
public class ErrorInfo {
    @Schema(description = "예외 클래스명", example = "SendGridEmailException")
    private String exception;

    @Schema(description = "에러메시지", example = "SendGrid 전송 실패 - 상태코드: 400")
    private String errorMessage;

    @Schema(description = "HTTP 상태 코드", example = "500")
    private int status;

    @Schema(description = "Request 추적ID", example = "asdf1fewresf132efcds")
    private String traceId; //고유번호주고 추적용이
}

