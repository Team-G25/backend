package com.g25.mailer.draft.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g25.mailer.draft.entity.TemporarySave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TemporarySaveResponse {
    private Long id;
    private ContentObj content;
    private LocalDate savedAt;


    public TemporarySaveResponse(TemporarySave temporarySave) {
        this.id = temporarySave.getId();
        this.savedAt = temporarySave.getSavedAt();
        try {
            this.content = new ObjectMapper().readValue(temporarySave.getContent(), ContentObj.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("content - JSON 파싱 실패", e);
        }
    }


    public TemporarySaveResponse(Long id, ContentObj content, LocalDate savedAt) {
        this.id = id;
        this.content = content;
        this.savedAt = savedAt;
    }
}
