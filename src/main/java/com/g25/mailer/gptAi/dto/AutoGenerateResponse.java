package com.g25.mailer.gptAi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Jackson 역직렬화를 위해 필요
@AllArgsConstructor
public class AutoGenerateResponse {
    private String content;
}
