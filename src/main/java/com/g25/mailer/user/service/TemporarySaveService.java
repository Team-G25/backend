package com.g25.mailer.user.service;

import com.g25.mailer.user.entity.TemporarySave;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.repository.TemporarySaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 임시저장
 */
@Service
@RequiredArgsConstructor
public class TemporarySaveService {

    private final TemporarySaveRepository temporarySaveRepository;

    // 임시저장 생성/저장
    public TemporarySave saveTemporary(TemporarySave temporarySave) {
        temporarySave.setSavedAt(LocalDateTime.now());
        return temporarySaveRepository.save(temporarySave);
    }

    // id로 단일 임시저장 조회
    public TemporarySave getTemporarySave(Long id) {
        return temporarySaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 임시저장 데이터가 존재하지 않습니다."));
    }

    // 사용자 기준으로 임시저장 목록 조회
    public List<TemporarySave> listTemporarySaves(User user) {
        return temporarySaveRepository.findByUser(user);
    }

    // 사용자 기준으로 임시저장 목록 전체 삭제 -> 삭제한 것 return
    public List<TemporarySave> deleteAllTemporarySaves(User user) {
        return temporarySaveRepository.deleteByUser(user);

    }

    // 사용자 기준으로 임시저장 목록 단건 삭제 -> 삭제한 것 return
    public List<TemporarySave> deleteSingleTemp(Long id, User user) {
        return temporarySaveRepository.deleteByIdAndUser(id, user);
    }
}
