package com.g25.mailer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequest {
    private String targetName;
    private String keyword1;
    private String keyword2;

}
