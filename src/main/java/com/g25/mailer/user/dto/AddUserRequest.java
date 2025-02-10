package com.g25.mailer.user.dto;

import com.g25.mailer.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

}


