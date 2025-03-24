package com.g25.mailer.template.controller;

import com.g25.mailer.common.CommonResponse;
import com.g25.mailer.template.dto.SendTemplateRequest;
import com.g25.mailer.template.dto.TemplateResponse;
import com.g25.mailer.template.service.TemplateService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
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
     * 템플릿 수정 후 이메일 전송
     * @param request
     * @return
     * @throws MessagingException
     */
    @PostMapping("/update-and-send")
    public ResponseEntity<CommonResponse<String>> updateAndSendEmail(@Valid @RequestBody SendTemplateRequest request) {
        try {
            templateService.sendEmailTemplate(request);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(CommonResponse.success("템플릿 메일 전송 완료"));
    }



    /**
     * 템플릿 수정후 임시저장(?)
     */




    /**
     * TODO 알려줄것
     * 임시저장, 보내기 기능
     * -> 클라이언트가...
     */

}
