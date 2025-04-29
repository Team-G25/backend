package com.g25.mailer.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용안함")
public class SendTemplateRequest {
    private Long templateId;
    private String recipientEmail; // 수신
    private String from;           // 송신
    private String customTitle;    // 변경할 제목
    private String customContent;  // 변경할 내용
    private List<String> attachmentKeys; //첨부파일

    public String getTo() {
        return recipientEmail;
    }
}
