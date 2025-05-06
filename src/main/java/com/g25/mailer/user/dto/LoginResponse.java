package com.g25.mailer.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "로그인 응답 DTO")
public class LoginResponse {
    @Schema(description = "회원 이메일", example = "user@mailergo.io.kr")
    private String email;

    @Schema(description = "회원 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "회원 프로필 이미지 URL", example = "s3.amazonaws.csom/buckesst/profsile.jpsg")
    private String profileImageUrl;
}