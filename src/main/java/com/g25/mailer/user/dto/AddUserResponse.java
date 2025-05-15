package com.g25.mailer.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "회원가입 응답")
public class AddUserResponse {
    @Schema(description = "userId(PK)", example = "14sfafd1r3")
    private Long userId;

    @Schema(description = "회원 닉네임", example = "nick")
    private String nickname;

    @Schema(description = "회원 이메일(사실상ID)", example = "user@mailergo.io.kr")
    private String email;

    @Schema(description = "회원 프로필 이미지 URL", example = "profile.jpg")
    private String profileImageUrl;
}
