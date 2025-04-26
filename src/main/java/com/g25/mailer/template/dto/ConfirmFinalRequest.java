package com.g25.mailer.template.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmFinalRequest {
    private String to;
    private String from;
    private String finalTitle;
    private String finalContent;
    private List<String> attachmentKeys;
}
