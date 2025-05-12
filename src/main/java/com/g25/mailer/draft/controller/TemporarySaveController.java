package com.g25.mailer.draft.controller;

import java.util.List;

import com.g25.mailer.draft.service.TemporarySaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.g25.mailer.draft.dto.TemporarySaveRequest;
import com.g25.mailer.draft.dto.TemporarySaveResponse;
import com.g25.mailer.draft.entity.TemporarySave;
import com.g25.mailer.user.entity.User;
import com.g25.mailer.user.service.UserDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * 임시메일 관련 API 컨트롤러
 *
 * 주요 변경 사항:
 * - 임시메일 저장 API의 요청 본문에서 content 필드가
 * - 기존 String → ContentObj (senderIdsubject, body 포함) 로 변경
 */
@Tag(name = "임시저장API", description = "임시메일저장, 전체조회, 전체삭제, 단건삭제 기능")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/temporary")
@RequiredArgsConstructor
@Slf4j
public class TemporarySaveController {

    private final TemporarySaveService temporarySaveService;
    private final UserDetailService userService;


    /**
     * 작성한 메일을 임시로 저장합니다.
     */
    @Operation(summary = "임시메일 저장", description = "작성한 메일을 임시로 저장합니다." +
            "{\n" +
            "  \"email\": \"1234@mailergo.io.kr\",\n" +
            "  \"content\": {\n" +
            "    \"senderId\": \"1234@mailergo.io.kr\", \n" +
            "    \"subject\": \"임시저장 테스트\",\n" +
            "    \"body\": \"현재 작성 중인 메일입니다.\"\n" +
            "  }\n" +
            "}\n")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/write")
    public ResponseEntity<TemporarySaveResponse> createTemporarySave(@RequestBody TemporarySaveRequest request) {
        log.info("임시 저장 content = {}", request.getContent());

        TemporarySaveResponse response = temporarySaveService.saveTemporary(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 임시메일함에 저장된 모든 메일 목록을 조회합니다.
     *
     * @return 임시메일 목록 응답 리스트
     */
    @Operation(summary = "임시 메일함 전체 조회", description = "임시메일함에 저장된 모든 메일 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/list") public ResponseEntity<List<TemporarySaveResponse>> listTemporarySaves() {
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
    @Operation(summary = "임시 메일함 전체 삭제", description = "임시메일함목록에 있는 임시메일을 모두 삭제합니다.")
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
    @Operation(summary = "임시 메일함 단건 삭제", description = "임시메일함목록에 있는 메일 한개를 삭제합니다.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSingleTemp(@PathVariable Long id) {
        temporarySaveService.deleteSingleTemp(id);
        log.info("단건 삭제 완료 : ID = {}", id);
        return ResponseEntity.noContent().build();
    }

}
