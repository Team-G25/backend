package com.g25.mailer.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {

    @Schema(description = "회원 이메일", example = "user@mailergo.io.kr")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Schema(description = "회원 비밀번호", example = "P@ssw0rd123")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
