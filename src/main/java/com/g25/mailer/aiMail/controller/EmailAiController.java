package com.g25.mailer.aiMail.controller;

import com.g25.mailer.aiMail.dto.AutoGenerateRequest;
import com.g25.mailer.aiMail.dto.AutoGenerateResponse;
import com.g25.mailer.aiMail.dto.RefineRequest;
import com.g25.mailer.aiMail.dto.RefineResponse;
import com.g25.mailer.aiMail.service.EmailAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@Tag(name = "AI 메일 서비스", description = "AI를 이용한 메일 자동 생성 및 교정 API")
public class EmailAiController {

    private final EmailAiService  emailAiService;

    /**
     * 자동생성
     * @param request
     * @return
     */
    @PostMapping("/generate")
    @Operation(summary = "AI 메일 자동 생성", description = "사용자가 입력한 프롬프트를 기반으로 AI가 메일 내용을 자동 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메일 내용 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<AutoGenerateResponse> generateEmail(@RequestBody AutoGenerateRequest request) {
        String content = emailAiService.generateEmailContent(request.getPrompt());
        return ResponseEntity.ok(new AutoGenerateResponse(content));
    }

    /**
     * 문법, 어투 교정
     * @param request
     * @return
     */
    @PostMapping("/refine")
    @Operation(summary = "AI 메일 문법·어투 교정", description = "사용자가 작성한 메일 내용을 AI가 문법과 어투를 교정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "교정된 메일 내용 반환"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<RefineResponse> refineEmail(@RequestBody RefineRequest request) {
        String refined = emailAiService.refineEmailContent(request.getContent());
        return ResponseEntity.ok(new RefineResponse(refined));
    }

}
