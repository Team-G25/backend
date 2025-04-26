package com.g25.mailer.template.controller;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.template.dto.SendTemplateRequest;
import com.g25.mailer.template.dto.TemplateResponse;
import com.g25.mailer.template.service.TemplateService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.g25.mailer.common.CommonResponse.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    /**
     * 템플릿 조회, 대상, 키워드1, 키워드2에 맞는 템플릿 가져온다.
     * @param targetName,keyword1, keyword2
     * @return
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
     * 템플릿 조회 + 수정 + 메일 전송
     */
    @PostMapping("/update-and-send")
    public ResponseEntity<CommonResponse<String>> updateAndSendEmail(@Valid @RequestBody SendTemplateRequest request) {
        try {
            // 1. 템플릿 조회
            TemplateResponse template = templateService.getTemplateById(request.getTemplateId());

            // 2. 템플릿 수정
            TemplateResponse modifiedTemplate = templateService.modifyTemplate(template, request);

            // 3. 메일 전송
            templateService.sendModifiedTemplate(
                    request.getFrom(),
                    request.getRecipientEmail(),
                    modifiedTemplate.getTitle(),
                    modifiedTemplate.getContent(),
                    (request.getAttachmentKeys() != null) ? request.getAttachmentKeys() : List.of()
            );

            return ResponseEntity.ok(success("템플릿 메일 전송 완료"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(CommonResponse.fail("잘못된 요청: " + e.getMessage()));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.fail("메일 전송 실패"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.fail("서버 오류: " + e.getMessage()));
        }
    }



    /**
     * TODO 알려줄것
     * 임시저장, 보내기 기능
     * -> 클라이언트가...
     */

}
