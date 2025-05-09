package com.g25.mailer.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "공통 에러 응답 정보")
public class ErrorInfo {

    @Builder.Default
    @Schema(description = "예외 클래스명", example = "SendGridEmailException")
    private String exception = "UnknownException";

    @Builder.Default
    @Schema(description = "에러메시지", example = "SendGrid 전송 실패 - 상태코드: 400")
    private String errorMessage = "에러 메시지 없음";

    @Builder.Default
    @Schema(description = "HTTP 상태 코드", example = "500")
    private int status = 500;

    @Builder.Default
    @Schema(description = "Request 추적ID", example = "asdf1fewresf132efcds")
    private String traceId = "unknown-trace-id";
}
