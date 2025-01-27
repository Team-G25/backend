package com.g25.mailer.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateReq {

    @NotEmpty(message = "로그인 ID는 필수입니다.")
    @Size(max = 50, message = "로그인 ID는 최대 50자입니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(max = 255, message = "비밀번호는 최대 255자입니다.")
    private String password;

    @NotEmpty(message = "이름은 필수입니다.")
    @Size(max = 50, message = "이름은 최대 50자입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 최대 100자입니다.")
    private String email;

    @Size(max = 15, message = "연락처는 최대 15자입니다.")
    private String contact;
}
