package com.g25.mailer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendTemplateRequest {
    private Long templateId;
    private String recipientEmail; // 수신
    private String from;           // 송신
    private String customTitle;    // 변경할 제목
    private String customContent;  // 변경할 내용
}
