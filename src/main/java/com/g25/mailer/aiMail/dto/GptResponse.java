package com.g25.mailer.aiMail.dto;

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
