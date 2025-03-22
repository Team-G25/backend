package com.g25.mailer.user.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String to; //송신자
    private String subject; //제목
    private String references; //참고
    private String content; //내용
    private String from; //수신자

}

