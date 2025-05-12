package com.g25.mailer.gptAi.dto;

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
