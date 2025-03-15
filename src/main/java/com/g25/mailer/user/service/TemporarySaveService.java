package com.g25.mailer.user.service;

import java.util.List;
import java.util.Optional;

import com.g25.mailer.user.dto.TemporarySaveResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.g25.mailer.user.entity.TemporarySave;
import com.g25.mailer.user.repository.TemporarySaveRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TemporarySaveService {

    private final TemporarySaveRepository temporarySaveRepository;


    // 임시저장 생성/저장
    public TemporarySaveResponse saveTemporary(TemporarySave temporarySave) {
        Optional<TemporarySave> existingSaveOpt = temporarySaveRepository.findByUserAndContent(
                temporarySave.getUser(), temporarySave.getContent());
        if (existingSaveOpt.isPresent()) {
            throw new IllegalStateException("이미 동일한 내용이 저장되어 있습니다.");
        }

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
