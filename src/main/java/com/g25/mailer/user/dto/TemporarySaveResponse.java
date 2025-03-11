package com.g25.mailer.user.dto;

import com.g25.mailer.user.entity.TemporarySave;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TemporarySaveResponse {
    private Long id;
    private String content;
    private LocalDateTime savedAt;

    public TemporarySaveResponse(TemporarySave temporarySave) {
        this.id = temporarySave.getId(); //메시지 일련번호
        this.content = temporarySave.getContent();
        this.savedAt = temporarySave.getSavedAt();
    }
    public static TemporarySaveResponse of(TemporarySave temporarySave) {
        return builder()
                .id(temporarySave.getId())
                .content(temporarySave.getContent())
                .savedAt(temporarySave.getSavedAt())
                .build();
    }

}
