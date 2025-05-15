package com.g25.mailer.common;

import lombok.Getter;

/**
 * 공통 API 응답 코드 Enum
 * - 코드 값은 API 응답의 returnCode에 담김
 */
@Getter
public enum ReturnCode {

    SUCCESS("0000", "요청 처리 성공"),
    UNKNOWN_ERROR("9999", "알 수 없는 서버 오류"), //일반 서버 오류
    SENDGRID_ERROR("5001", "SendGrid 메일 전송 오류"), //SendGrid 관련 오류
    UNAUTHORIZED("401", "로그인이 필요합니다.");

    private String code; //응답 코드
    private String text; // 코드 설명

    ReturnCode(String code, String text) {
        this.code = code;
        this.text = text;
    }

}
