package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.TemporarySaveRequest;
import com.g25.mailer.user.entity.TemporarySave;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.service.TemporarySaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TemporarySaveController {

    private final TemporarySaveService temporarySaveService;

    //[API]임시저장 생성 POST
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

    //[API]단일 임시저장 조회 GET
    @GetMapping("/{id}")
    public ResponseEntity<TemporarySave> getTemporarySave(@PathVariable Long id) {
        TemporarySave temporarySave = temporarySaveService.getTemporarySave(id);
        return ResponseEntity.ok(temporarySave);
    }

    //[API]임시저장 목록 조회 GET
    @GetMapping
    public ResponseEntity<List<TemporarySave>> listTemporarySaves(@AuthenticationPrincipal User user) {
        List<TemporarySave> saves = temporarySaveService.listTemporarySaves(user);
        return ResponseEntity.ok(saves);
    }

    // [API] 임시저장 목록 전체 삭제
    public ResponseEntity<List<TemporarySave>> deleteAllTemp(@AuthenticationPrincipal User user) {
        List<TemporarySave> theDeleted = temporarySaveService.deleteAllTemporarySaves(user);
        log.info("전체 삭제 완료");
        return ResponseEntity.ok(theDeleted);
    }


    // [API] 임시저장 목록 단건 삭제
    public ResponseEntity<List<TemporarySave>> deleteSingleTemp(@PathVariable Long id, @AuthenticationPrincipal User user){
        List<TemporarySave> theDeleted = temporarySaveService.deleteSingleTemp(id, user);
        log.info("단건 삭제 완료");
        return ResponseEntity.ok(theDeleted);
    }
}
