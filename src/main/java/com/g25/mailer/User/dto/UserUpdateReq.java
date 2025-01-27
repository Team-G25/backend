package com.g25.mailer.User.dto;

import com.g25.mailer.User.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateReq {

    @Size(max = 50, message = "이름은 최대 50자입니다.")
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 최대 100자입니다.")
    private String email;

    @Size(max = 15, message = "연락처는 최대 15자입니다.")
    private String contact;

    private User.Role role;
    private User.Status status;
}
