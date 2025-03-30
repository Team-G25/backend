package com.g25.mailer.aiMail.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GptMessage {
    private String role;
    private String content;
}
