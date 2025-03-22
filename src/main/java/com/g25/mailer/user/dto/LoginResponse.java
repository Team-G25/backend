package com.g25.mailer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String nickname;
    private String profileImageUrl;
}
