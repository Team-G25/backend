package com.g25.mailer.email.exception;

/**
 * SendGrid 메일 전송 중 발생하는 예외 클래스.
 * 주로 SendGrid API 호출 실패나 잘못된 응답을 감지할 때 발생.
 */
public class SendGridEmailException extends RuntimeException {
    public SendGridEmailException(String message) {
        super(message);
    }

    public SendGridEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
