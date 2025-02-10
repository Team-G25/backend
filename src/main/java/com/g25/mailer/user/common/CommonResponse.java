package com.g25.mailer.user.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CommonResponse<T> {
    private String returnCode;
    private String returnMessage;
    private T info;

    public CommonResponse(ReturnCode returnCode) {
        this.returnCode = returnCode.getCode();
        this.returnMessage = returnCode.getText();
    }

    public CommonResponse(ReturnCode returnCode, T info) {
        this.returnCode = returnCode.getCode();
        this.returnMessage = returnCode.getText();
        this.info = info;
    }

    public static <T> CommonResponse<T> of(ReturnCode returnCode, T info) {
        return new CommonResponse(returnCode, info);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse(ReturnCode.SUCCESS);
    }

    public static <T> CommonResponse<T> success(T info) {
        return new CommonResponse(ReturnCode.SUCCESS, info);
    }
}
