package com.g25.mailer.template.controller;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.template.dto.*;
import com.g25.mailer.template.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
    /*
    1. GET    /templates/search         → 템플릿 리스트 조회
    2. POST   /templates/customize      → 사용자 커스터마이징 저장
    3. POST   /templates/ai-refine      → AI 피드백 요청
     */
@Tag(name = "템플릿메일API", description = "템플릿 조회, 수정, AI 피드백 기능")
@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    /**
     * Target과 keyword1, keyword2를 기준으로 템플릿을 조회합니다
     * @param targetName
     * @param keyword1
     * @param keyword2
     * @return
     */
    @Operation(summary = "템플릿 조회", description = "Target과 keyword1, keyword2를 기준으로 템플릿을 조회")
    @GetMapping("/search")
    public ResponseEntity<List<TemplateResponse>> getTemplates(
            @RequestParam String targetName,
            @RequestParam String keyword1,
            @RequestParam(required = false) String keyword2) {
        List<TemplateResponse> templates = templateService.getTemplates(targetName, keyword1, keyword2);
        return ResponseEntity.ok(templates);
    }

    /**
     * 템플릿 커스터마이징 : 제공된 템플릿을 사용자가 수정
     * @param request
     * @return
     */
    @Operation(summary = "템플릿 커스터마이징 저장", description = "재공된 템플릿을 수정한 내용을 저장")
    @PostMapping("/customize")
    public ResponseEntity<CommonResponse<String>> customizeTemplate(@RequestBody CustomizeTemplateRequest request) {
        templateService.saveCustomizedTemplate(request);
        return ResponseEntity.ok(CommonResponse.success("수정 저장 완료"));
    }

    /**
     * 수정된 템플릿 내용을 AI 보정
     * @param request
     * @return
     */
    @Operation(summary = "AI 피드백 요청", description = "수정한 템플릿 내용을 AI 보정을 요청")
    @PostMapping("/ai-made")
    public ResponseEntity<AiRefineCustomResponse> aiRefineCustom(@RequestBody AiRefineCustomRequest request) {
        String refinedContent = templateService.refineCustomContent(request.getCustomContent());
        return ResponseEntity.ok(new AiRefineCustomResponse(refinedContent));
    }


    /**
     *최종 확정된 수정본으로 메일 발송 - 메일송신
     * @param request
     * @return
     */
    @Operation(summary = "템플릿 메일 발송", description = "사용안함")
    @PostMapping("/confirm-final")
    public ResponseEntity<CommonResponse<String>> confirmFinal(@RequestBody ConfirmFinalRequest request) {
        templateService.sendFinalEmail(request);
        return ResponseEntity.ok(CommonResponse.success("템플릿커스텀 및 ai피드백받은 메일 발송 완료"));
    }
}
