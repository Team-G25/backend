package com.g25.mailer.common.handler;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.common.ReturnCode;
import com.g25.mailer.common.dto.ErrorInfo;
import com.g25.mailer.email.exception.SendGridEmailException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * SendGridEmailException 처리 메서드
     *
     * 이 메서드는 SendGrid 메일 전송 중 발생한 예외를 잡아
     * 표준화된 에러 응답으로 클라이언트에 반환.
     * - 고유 traceId를 생성해 로그와 응답에 포함
     * - ReturnCode.SENDGRID_ERROR 코드로 응답 포맷 고정
     *
     * @param ex SendGridEmailException 발생 예외
     * @return CommonResponse<ErrorInfo> 형식의 표준 에러 응답
     */
    @ExceptionHandler(SendGridEmailException.class)
    public ResponseEntity<CommonResponse<ErrorInfo>> handleSendGridException(SendGridEmailException ex) {
        String traceId = UUID.randomUUID().toString();

        log.error("[traceId: {}] SendGridEmailException 발생: {}", traceId, ex.getMessage(), ex);

        ErrorInfo errorInfo = ErrorInfo.builder()
                .exception(ex.getClass().getSimpleName())
                .errorMessage(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .traceId(traceId)
                .build();

        return ResponseEntity.internalServerError()
                .body(CommonResponse.of(ReturnCode.SENDGRID_ERROR, errorInfo));
    }


    /**
     * 일반 Exception 처리 메서드
     *
     * 이 메서드는 프로젝트에서 발생하는 모든 알 수 없는 예외를 잡아
     * 표준화된 에러 응답으로 클라이언트에 반환.
     * - 고유 traceId를 생성해 로그와 응답에 포함
     * - ReturnCode.UNKNOWN_ERROR 코드로 응답 포맷 고정
     *
     * @param ex Exception 발생 예외
     * @return CommonResponse<ErrorInfo> 형식의 표준 에러 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<ErrorInfo>> handleGeneralException(Exception ex) {
        String traceId = UUID.randomUUID().toString();

        log.error("[traceId: {}] Unhandled Exception 발생: {}", traceId, ex.getMessage(), ex);

        ErrorInfo errorInfo = ErrorInfo.builder()
                .exception(ex.getClass().getSimpleName())
                .errorMessage(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .traceId(traceId)
                .build();

        return ResponseEntity.internalServerError()
                .body(CommonResponse.of(ReturnCode.UNKNOWN_ERROR, errorInfo));
    }
}
