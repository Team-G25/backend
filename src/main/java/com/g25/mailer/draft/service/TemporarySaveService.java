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

import static com.g25.mailer.common.util.DomainUtils.domainChangeId;

@Service
@RequiredArgsConstructor
@Transactional
public class TemporarySaveService {

    private final TemporarySaveRepository temporarySaveRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); //ContentObj → JSON 문자열 파싱


    //임시저장 생성 및 저정
    public TemporarySaveResponse saveTemporary(TemporarySaveRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("없는 유저입니다"));

        ContentObj contentObj = request.getContent();
        contentObj.setSenderId(domainChangeId(contentObj.getSenderId())); // 유틸메서드 적용

        String jsonContent;
        try {

            jsonContent = objectMapper.writeValueAsString(contentObj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("content 변환 실패", e);
        }

        Optional<TemporarySave> existingSaveOpt = temporarySaveRepository.findByUserAndContent(
                user, jsonContent);
        if (existingSaveOpt.isPresent()) {
            throw new IllegalStateException("이미 작성한 메일입니다 새로운 내용만 가능합니다.");
        }

        TemporarySave temporarySave = TemporarySave.builder()
                .user(user)
                .content(jsonContent)
                .build();

        TemporarySave saved = temporarySaveRepository.save(temporarySave);
        return new TemporarySaveResponse(saved.getId(), contentObj, saved.getSavedAt());
    }

    @Transactional(readOnly = true)
    public TemporarySave getTemporarySave(Long id) {
        return temporarySaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 임시저장 데이터가 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public List<TemporarySave> listTemporarySaves() {
        return temporarySaveRepository.findAll();
    }


    public void deleteAllTemporarySaves() {
        temporarySaveRepository.deleteAll();
    }

    public void deleteSingleTemp(Long id) {
        temporarySaveRepository.deleteById(id);
    }




}
