package com.g25.mailer.user.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject; //제목
    private String content;

}

