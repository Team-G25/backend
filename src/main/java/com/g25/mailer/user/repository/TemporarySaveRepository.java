package com.g25.mailer.user.repository;

import com.g25.mailer.user.entity.TemporarySave;
import com.g25.mailer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemporarySaveRepository extends JpaRepository<TemporarySave, Long> {
    // 특정 사용자에 대한 임시저장 목록 조회
    List<TemporarySave> findByUser(User user);
    int deleteByUser(User user);
    int deleteByIdAndUser(Long id, User user);
    Optional<TemporarySave> findByUserAndContent(User user, String content);
}
