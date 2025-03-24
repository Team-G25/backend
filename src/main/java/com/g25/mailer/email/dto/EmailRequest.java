package com.g25.mailer.email.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class EmailRequest {
    private String to; //송신자
    private String subject; //제목
    private String references; //참고
    private String content; //내용
    private String from; //수신자
    private List<MultipartFile> attachments; //다중파일


}

