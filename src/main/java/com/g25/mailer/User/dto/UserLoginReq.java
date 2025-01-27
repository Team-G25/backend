package com.g25.mailer.User.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginReq {
    private String loginId;
    private String password;

}

