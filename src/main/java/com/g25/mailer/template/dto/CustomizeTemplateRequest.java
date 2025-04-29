package com.g25.mailer.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "템플릿메일 수정요청")
public class CustomizeTemplateRequest {
    private Long templateId;
    private String customTitle;
    private String customContent;
    private Long userId;

}
