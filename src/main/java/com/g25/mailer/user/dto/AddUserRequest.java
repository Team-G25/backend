package com.g25.mailer.user.dto;

import com.g25.mailer.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {

    private String nickname;
    @NotNull
    private String email;
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
