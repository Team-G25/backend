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
public class ConfirmFinalRequest {
    private String to;
    private String from;
    private String finalTitle;
    private String finalContent;
    private List<String> attachmentKeys;
}
