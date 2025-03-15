package com.g25.mailer.user.service;

import com.g25.mailer.user.entity.Target;
import com.g25.mailer.user.repository.TargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;
    /**
     * target찾기
     */
    public Optional<Target> getTargetByName(String targetName) {
        return targetRepository.findByTargetName(targetName);
    }
}
