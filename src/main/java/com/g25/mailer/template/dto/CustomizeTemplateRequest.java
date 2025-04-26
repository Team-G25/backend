package com.g25.mailer.template.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomizeTemplateRequest {
    private Long templateId;
    private String customTitle;
    private String customContent;
    private Long userId;

}
