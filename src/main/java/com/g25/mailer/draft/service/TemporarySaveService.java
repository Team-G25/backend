package com.g25.mailer.draft.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.g25.mailer.draft.dto.ContentObj;
import com.g25.mailer.draft.dto.TemporarySaveRequest;
import com.g25.mailer.draft.dto.TemporarySaveResponse;
import com.g25.mailer.draft.entity.TemporarySave;
import com.g25.mailer.draft.repository.TemporarySaveRepository;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TemporarySaveService {

    private final TemporarySaveRepository temporarySaveRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); //ContentObj → JSON 문자열


    // 임시저장 생성/저장
    public TemporarySaveResponse saveTemporary(TemporarySaveRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        String jsonContent;
        try {
            ContentObj contentObj = request.getContent();
            jsonContent = objectMapper.writeValueAsString(contentObj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Content 변환 실패", e);
        }

        Optional<TemporarySave> existingSaveOpt = temporarySaveRepository.findByUserAndContent(
                user, jsonContent);
        if (existingSaveOpt.isPresent()) {
            throw new IllegalStateException("이미 동일한 내용이 저장되어 있습니다.");
        }

        TemporarySave temporarySave = TemporarySave.builder()
                .user(user)
                .content(jsonContent)
                .build();

        TemporarySave saved = temporarySaveRepository.save(temporarySave);
        return TemporarySaveResponse.of(saved);
    }


    // id로 단일 임시저장 조회
    @Transactional(readOnly = true)
    public TemporarySave getTemporarySave(Long id) {
        return temporarySaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 임시저장 데이터가 존재하지 않습니다."));
    }


    // 모든 임시저장 목록 조회
    @Transactional(readOnly = true)
    public List<TemporarySave> listTemporarySaves() {
        return temporarySaveRepository.findAll();
    }


    // 모든 임시저장 삭제
    public void deleteAllTemporarySaves() {
        temporarySaveRepository.deleteAll();
    }

    // 단일 임시저장 삭제
    public void deleteSingleTemp(Long id) {
        temporarySaveRepository.deleteById(id);
    }
}
