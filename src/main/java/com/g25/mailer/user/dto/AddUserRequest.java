package com.g25.mailer.user.dto;

import com.g25.mailer.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원가입 요청 DTO")
public class AddUserRequest {

    @Schema(description = "회원 닉네임/이름", example = "홍길동")
    private String nickname;

    @Schema(description = "회원 이메일", example = "user@mailergo.io.kr")
    @NotNull
    private String email;

    @Schema(description = "회원 비밀번호", example = "P@ssw0rd123")
    @NotNull
    private String password;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();
    }



}
