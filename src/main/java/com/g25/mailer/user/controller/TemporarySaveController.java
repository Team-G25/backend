package com.g25.mailer.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g25.mailer.user.dto.TemporarySaveRequest;
import com.g25.mailer.user.dto.TemporarySaveResponse;
import com.g25.mailer.user.entity.TemporarySave;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.service.TemporarySaveService;
import com.g25.mailer.user.service.UserDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/temporary-saves")
@RequiredArgsConstructor
@Slf4j
public class TemporarySaveController {

    private final TemporarySaveService temporarySaveService;
    private final UserDetailService userService;

    @PostMapping("/write")
    public ResponseEntity<TemporarySaveResponse> createTemporarySave(@RequestBody TemporarySaveRequest request) {
        log.info("임시 저장 content = {}", request.getContent());
        User user = userService.getUserByEmail(request.getEmail());
        TemporarySave temporarySave = TemporarySave.builder()
                .content(request.getContent())
                .user(user)
                .build();
        TemporarySaveResponse response = temporarySaveService.saveTemporary(temporarySave);
        return ResponseEntity.ok(response);
    }

    // [API] 목록 조회 (사용자 인증 제거는 미구현)
    @GetMapping("/list")
    public ResponseEntity<List<TemporarySaveResponse>> listTemporarySaves() {
        List<TemporarySave> saves = temporarySaveService.listTemporarySaves();
        List<TemporarySaveResponse> response = saves.stream()
                .map(TemporarySaveResponse::of)
                .toList();

        return ResponseEntity.ok(response);
    }

    // [API] 임시저장 목록 전체 삭제 (사용자 인증 제거)
    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllTemp() {
        temporarySaveService.deleteAllTemporarySaves();
        log.info("전체 삭제 완료");
        return ResponseEntity.noContent().build();
    }

    // [API] 임시저장 목록 단건 삭제 (사용자 인증 제거)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSingleTemp(@PathVariable Long id) {
        temporarySaveService.deleteSingleTemp(id);
        log.info("단건 삭제 완료 : ID = {}", id);
        return ResponseEntity.noContent().build();
    }

}
