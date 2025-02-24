package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.TemporarySaveRequest;
import com.g25.mailer.user.entity.TemporarySave;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.service.TemporarySaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 메일 임시 저장 v1
 */
@RestController
@RequestMapping("/api/temporary-saves")
@RequiredArgsConstructor
public class TemporarySaveController {

    private final TemporarySaveService temporarySaveService;

    //임시저장 생성 POST
    @PostMapping
    public ResponseEntity<TemporarySave> createTemporarySave(
            @AuthenticationPrincipal User user,
            @RequestBody TemporarySaveRequest request) {

        TemporarySave temporarySave = TemporarySave.builder()
                .user(user)
                .content(request.getContent())
                .build();

        TemporarySave saved = temporarySaveService.saveTemporary(temporarySave);
        return ResponseEntity.ok(saved);
    }

    //단일 임시저장 조회 GET
    @GetMapping("/{id}")
    public ResponseEntity<TemporarySave> getTemporarySave(@PathVariable Long id) {
        TemporarySave temporarySave = temporarySaveService.getTemporarySave(id);
        return ResponseEntity.ok(temporarySave);
    }

    //임시저장 목록 조회 GETs
    @GetMapping
    public ResponseEntity<List<TemporarySave>> listTemporarySaves(@AuthenticationPrincipal User user) {
        List<TemporarySave> saves = temporarySaveService.listTemporarySaves(user);
        return ResponseEntity.ok(saves);
    }
}
