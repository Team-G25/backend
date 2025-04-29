package com.g25.mailer.draft.dto;

import com.g25.mailer.draft.entity.TemporarySave;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class TemporarySaveResponse {
    private Long id;
    private String content;
    private LocalDate savedAt;

    // @Builder어노테이션이 이 메소드를  생성자로 쓰임
    public TemporarySaveResponse(Long id, String content, LocalDate savedAt) {
        this.id = id;
        this.content = content;
        this.savedAt = savedAt;
    }

    //엔티티를 dto로 변환시킴, 엔티티 기반 생성자
    public TemporarySaveResponse(TemporarySave temporarySave) {
        this(temporarySave.getId(), temporarySave.getContent(), temporarySave.getSavedAt());
    }

    public static TemporarySaveResponse of(TemporarySave saved) {
        return builder()
                .id(saved.getId())
                .content(saved.getContent())
                .savedAt(saved.getSavedAt())
                .build();
    }
}
