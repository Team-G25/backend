package com.g25.mailer.draft.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContentObj {
    private String subject;
    private String body;
    private List<String> attachments;
}
