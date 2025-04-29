package com.g25.mailer.template.controller;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.template.dto.*;
import com.g25.mailer.template.service.CustomizedTemplateService;
import com.g25.mailer.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final CustomizedTemplateService customizedTemplateService;

    /**
     * 템플릿 리스트 조회
     */
    @GetMapping("/search")
    public ResponseEntity<List<TemplateResponse>> getTemplates(
            @RequestParam String targetName,
            @RequestParam String keyword1,
            @RequestParam(required = false) String keyword2) {
        List<TemplateResponse> templates = templateService.getTemplates(targetName, keyword1, keyword2);
        return ResponseEntity.ok(templates);
    }

    /**
     * 템플릿 단일 조회
     */
//    @GetMapping("/{templateId}")
//    public ResponseEntity<TemplateResponse> getTemplateById(@PathVariable Long templateId) {
//        TemplateResponse template = templateService.getTemplateById(templateId);
//        return ResponseEntity.ok(template);
//    }

    /**
     * 템플릿 커스터마이징 수정 저장(사용자가 제공된 템플릿을 수정 ex. 이름 등)
     */
    @PostMapping("/customize")
    public ResponseEntity<CommonResponse<String>> customizeTemplate(@RequestBody CustomizeTemplateRequest request) {
        templateService.saveCustomizedTemplate(request);
        return ResponseEntity.ok(CommonResponse.success("수정 저장 완료"));
    }

    /**
     * 수정된 템플릿 내용을 AI로 보정 - 작성한 이메일을 AI피드백받기
     */
    @PostMapping("/ai-refine-custom")
    public ResponseEntity<AiRefineCustomResponse> aiRefineCustom(@RequestBody AiRefineCustomRequest request) {
        String refinedContent = templateService.refineCustomContent(request.getCustomContent());
        return ResponseEntity.ok(new AiRefineCustomResponse(refinedContent));
    }


    /**
     * 작성중인 템플릿을 최종 확정(FINAL) 처리 - 템플릿을 사용한 메일 작성 최종 완료
     */
    @PostMapping("/customize/confirm")
    public ResponseEntity<CommonResponse<String>> confirmFinalCustomizedTemplate(
            @RequestParam Long userId,
            @RequestParam Long templateId) {

        customizedTemplateService.confirmFinalCustomizedTemplate(userId, templateId);
        return ResponseEntity.ok(CommonResponse.success("커스터마이징 템플릿 최종 확정 완료"));
    }

    /**
     * 최종 확정된 수정본으로 메일 발송 - 메일송신
     */
    @PostMapping("/confirm-final")
    public ResponseEntity<CommonResponse<String>> confirmFinal(@RequestBody ConfirmFinalRequest request) {
        templateService.sendFinalEmail(request);
        return ResponseEntity.ok(CommonResponse.success("AI 수정본 메일 발송 완료"));
    }
}
