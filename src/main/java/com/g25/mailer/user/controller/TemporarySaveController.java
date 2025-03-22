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
@RequestMapping("/temporary")
@RequiredArgsConstructor
@Slf4j
public class TemporarySaveController {

    private final TemporarySaveService temporarySaveService;
    private final UserDetailService userService;


    /**
     * 메일작성
     * @param request
     * @return
     */
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


    /**
     * 임시 메일함 전체 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<TemporarySaveResponse>> listTemporarySaves() {
        List<TemporarySave> saves = temporarySaveService.listTemporarySaves();
        List<TemporarySaveResponse> response = saves.stream()
                .map(TemporarySaveResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * 임시 메일함에서 전체 삭제
     * @return
     */

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllTemp() {
        temporarySaveService.deleteAllTemporarySaves();
        log.info("전체 삭제 완료");
        return ResponseEntity.noContent().build();
    }

    /**
     * 임시 메일함에서 단건 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSingleTemp(@PathVariable Long id) {
        temporarySaveService.deleteSingleTemp(id);
        log.info("단건 삭제 완료 : ID = {}", id);
        return ResponseEntity.noContent().build();
    }

}
