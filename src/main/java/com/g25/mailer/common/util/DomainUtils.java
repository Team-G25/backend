package com.g25.mailer.common.util;

public class DomainUtils {

    private static final String MAILERGO_DOMAIN = "@mailergo.io.kr";

    /**
     * 유저 아이디(이메일)에서 @상단만 남기고 기존이메일주소는 @mailergo.io.kr으로 교체
     *  "1234@naver.com" → "1234@mailergo.io.kr"
     */
    public static String domainChangeId(String rawSenderId) {
        if (rawSenderId == null || rawSenderId.isBlank()) {
            throw new IllegalArgumentException("발신자주소를 입력하세요");
        }
        String localPart = rawSenderId.contains("@")
                ? rawSenderId.substring(0, rawSenderId.indexOf("@"))
                : rawSenderId;

        return localPart + MAILERGO_DOMAIN;
    }


}
