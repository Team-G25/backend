package com.g25.mailer.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Schema(description = "공통 API 응답 포맷")
public class CommonResponse<T>  {
    @Schema(description = "응답 코드", example = "0000")
    private String returnCode;
    @Schema(description = "응답 메시지", example = "Success.")
    private String returnMessage;
    @Schema(description = "실제 응답 데이터 또는 에러 정보", nullable = true)
    private T info;


    public CommonResponse(ReturnCode returnCode, T info) {
        this.returnCode = returnCode.getCode();
        this.returnMessage = returnCode.getText();
        this.info = info;
    }

    public static <T> CommonResponse<T> of(ReturnCode returnCode, T info) {
        return new CommonResponse<>(returnCode, info);
    }

    public static <T> CommonResponse<T> success(T info) {
        return new CommonResponse<>(ReturnCode.SUCCESS, info);
    }

    public static <T> CommonResponse<T> fail(T info) {
        return new CommonResponse<>(ReturnCode.UNKNOWN_ERROR, info);
    }
}
