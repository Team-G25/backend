package com.g25.mailer.gptAi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GptResponse {
    private List<GptChoice> choices;
}
