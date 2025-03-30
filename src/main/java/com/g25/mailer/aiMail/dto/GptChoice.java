package com.g25.mailer.aiMail.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GptChoice {
    private GptMessage message;
    private String finish_reason;
    private int index;
}
