package com.g25.mailer.user.common;

import lombok.Getter;

@Getter
public enum ReturnCode {
    SUCCESS("0000", "Success."),
    UNKNOWN_ERROR("9999", "Unable to process your request.");


    private String code;
    private String text;

    ReturnCode(String code, String text) {
        this.code = code;
        this.text = text;
    }

}
