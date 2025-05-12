package com.g25.mailer.template.service;

import com.g25.mailer.gptAi.service.EmailAiService;
import com.g25.mailer.email.service.EmailService;
import com.g25.mailer.template.dto.ConfirmFinalRequest;
import com.g25.mailer.template.dto.CustomizeTemplateRequest;
import com.g25.mailer.template.dto.SendTemplateRequest;
import com.g25.mailer.template.dto.TemplateResponse;
import com.g25.mailer.template.entity.CustomizedTemplate;
import com.g25.mailer.template.entity.Keyword;
import com.g25.mailer.template.entity.Target;
import com.g25.mailer.template.entity.Template;
import com.g25.mailer.template.repository.CustomizedTemplateRepository;
import com.g25.mailer.template.repository.KeywordRepository;
import com.g25.mailer.template.repository.TargetRepository;
import com.g25.mailer.template.repository.TemplateRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final KeywordRepository keywordRepository;
    private final TargetRepository targetRepository;
    private final EmailService emailService;
    private final EmailAiService emailAiService;
    private final CustomizedTemplateRepository customizedTemplateRepository;


    /**
     * 템플릿조회
     * @param targetName
     * @param keyword1
     * @param keyword2
     * @return
     */
    public List<TemplateResponse> getTemplates(String targetName, String keyword1, String keyword2) {
        Target target = targetRepository.findByTargetName(targetName)
                .orElseThrow(() -> new IllegalArgumentException("No Target: " + targetName));

        Keyword key1 = keywordRepository.findByKeyword(keyword1)
                .orElseThrow(() -> new IllegalArgumentException("No Keyword: " + keyword1));

        List<Template> templates;
        if (keyword2 == null || keyword2.isBlank()) {
            templates = templateRepository.findByTargetAndKeyword1AndKeyword2IsNull(target, key1.getKeyword());
        } else {
            Keyword key2 = keywordRepository.findByKeyword(keyword2)
                    .orElseThrow(() -> new IllegalArgumentException("No Keyword: " + keyword2));

            templates = templateRepository.findByTargetAndKeyword1AndKeyword2Equals(target, key1.getKeyword(), key2.getKeyword());
        }

        // 생성자 재사용
        return templates.stream()
                .map(TemplateResponse::new)
                .toList();
    }


    /**
     * 템플릿 ID로 조회
     */
    public TemplateResponse getTemplateById(Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("조회되는 템플릿이 없습니다."));
        return new TemplateResponse(template);
    }

    /**
     * 템플릿 커스터마이징 수정
     */
    public TemplateResponse modifyTemplate(TemplateResponse template, SendTemplateRequest request) {
        String title = isNotBlank(request.getCustomTitle()) ? request.getCustomTitle() : template.getTitle();
        String content = isNotBlank(request.getCustomContent()) ? request.getCustomContent() : template.getContent();

        return new TemplateResponse(
                template.getTemplateId(),
                title,
                content,
                template.getTargetName(),
                template.getKeyword1(),
                template.getKeyword2()
        );
    }

    /**
     * 수정본을 저장 (현재는 저장로직 없음, 필요 시 추가)
     */
    @Transactional
    public void saveCustomizedTemplate(CustomizeTemplateRequest request) {
        CustomizedTemplate customizedTemplate = customizedTemplateRepository
                .findByTemplateIdAndUserIdAndStatus(request.getTemplateId(), request.getUserId(), "DRAFT")
                .orElse(null);

        if (customizedTemplate != null) {
            // 기존 임시 저장본 있으면 수정
            customizedTemplate.setCustomTitle(request.getCustomTitle());
            customizedTemplate.setCustomContent(request.getCustomContent());
            customizedTemplate.setCreatedAt(LocalDateTime.now());

            customizedTemplateRepository.save(customizedTemplate);
            log.info("기존 임시 저장본 업데이트 완료: id = {}, title = {}", customizedTemplate.getId(), customizedTemplate.getCustomTitle());
        } else {
            // 없으면 새로 저장
            CustomizedTemplate newTemplate = new CustomizedTemplate();
            newTemplate.setTemplateId(request.getTemplateId());
            newTemplate.setCustomTitle(request.getCustomTitle());
            newTemplate.setCustomContent(request.getCustomContent());
            newTemplate.setUserId(request.getUserId());
            newTemplate.setStatus("DRAFT");
            newTemplate.setCreatedAt(LocalDateTime.now());

            customizedTemplateRepository.save(newTemplate);
            log.info("새로운 임시 저장본 저장 완료: id = {}, title = {}", newTemplate.getId(), newTemplate.getCustomTitle());
        }

        log.info("사용자 수정 저장 요청 최종 완료: title = {}, content = {}", request.getCustomTitle(), request.getCustomContent());
    }


    /**
     * 사용자 수정본을 AI로 보정
     */
    public String refineCustomContent(String customContent) {
        return emailAiService.refineEmailContent(customContent);
    }

    /**
     * 수정된 템플릿 메일 전송
     */
    public void sendModifiedTemplate(String from, String to, String title, String content, List<String> attachmentKeys) throws MessagingException {
        if (attachmentKeys == null || attachmentKeys.isEmpty()) {
            emailService.sendSimpleMail(to, title, content, from);
        } else {
            emailService.sendMailWithAttachment(to, title, content, from, attachmentKeys);
        }
    }

    private boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }

    /**
     * 최종 확정된 수정본으로 메일 발송
     */
    public void sendFinalEmail(ConfirmFinalRequest request) {
        List<String> attachmentKeys = (request.getAttachmentKeys() != null) ? request.getAttachmentKeys() : List.of();

        if (attachmentKeys.isEmpty()) {
            emailService.sendSimpleMail(
                    request.getTo(),
                    request.getFinalTitle(),
                    request.getFinalContent(),
                    request.getFrom()
            );
        } else {
            emailService.sendMailWithAttachment(
                    request.getTo(),
                    request.getFinalTitle(),
                    request.getFinalContent(),
                    request.getFrom(),
                    attachmentKeys
            );
        }
    }


}
