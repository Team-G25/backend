package com.g25.mailer.user.dto;

import com.g25.mailer.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddUserResponse {
    private String email;

}
