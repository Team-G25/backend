package com.g25.mailer.user.controller;

import com.g25.mailer.user.dto.TemplateResponse;
import com.g25.mailer.user.service.TemplateService;

import jakarta.mail.MessagingException;
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
     * 대상, 키워드1, 키워드2에 맞는 템플릿을
     * @param
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<TemplateResponse>> getTemplate(
            @RequestParam String targetName,
            @RequestParam String keyword1,
            @RequestParam(required = false) String keyword2) {

        List<TemplateResponse> templates = templateService.getTemplates(targetName, keyword1, keyword2);
        return ResponseEntity.ok(templates);
    }

    /**
     * 템플릿 메일 전송 기능(템플릿 수정후)
     * @param templateId
     * @param recipientEmail
     * @param customContent
     * @return
     * @throws MessagingException
     */
    @PostMapping("/send")
    public ResponseEntity<Void> sendTemplateEmail(
            @RequestParam Long templateId,
            @RequestParam String recipientEmail,
            @RequestBody String customContent) throws MessagingException {
        templateService.sendEmailTemplate(templateId, recipientEmail, customContent);
        return ResponseEntity.ok().build();
    }

}
