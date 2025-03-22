package com.g25.mailer.user.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    @NotBlank(message = "새 비밀번호를 입력하세요.")
    private String newPassword;
}
