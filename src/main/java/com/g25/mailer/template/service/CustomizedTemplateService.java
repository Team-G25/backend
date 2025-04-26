package com.g25.mailer.template.service;

import com.g25.mailer.template.dto.CustomizeTemplateRequest;
import com.g25.mailer.template.entity.CustomizedTemplate;
import com.g25.mailer.template.repository.CustomizedTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomizedTemplateService {

    private final CustomizedTemplateRepository customizedTemplateRepository;

    @Transactional
    public void saveCustomizedTemplate(CustomizeTemplateRequest request) {
        CustomizedTemplate existing = customizedTemplateRepository
                .findByTemplateIdAndUserIdAndStatus(
                        request.getTemplateId(),
                        request.getUserId(),
                        "DRAFT"
                )
                .orElse(null);

        if (existing != null) {
            existing.setCustomTitle(request.getCustomTitle());
            existing.setCustomContent(request.getCustomContent());
            existing.setCreatedAt(LocalDateTime.now());

            customizedTemplateRepository.save(existing);

            log.info("커스텀 템플릿 덮어쓰기 완료: id = {}, title = {}", existing.getId(), existing.getCustomTitle());
        } else {
            CustomizedTemplate newTemplate = new CustomizedTemplate();
            newTemplate.setTemplateId(request.getTemplateId());
            newTemplate.setCustomTitle(request.getCustomTitle());
            newTemplate.setCustomContent(request.getCustomContent());
            newTemplate.setUserId(request.getUserId());
            newTemplate.setStatus("DRAFT");
            newTemplate.setCreatedAt(LocalDateTime.now());

            customizedTemplateRepository.save(newTemplate);

            log.info("커스텀 템플릿 새로 저장 완료: title = {}", newTemplate.getCustomTitle());
        }
    }

    @Transactional(readOnly = true)
    public CustomizedTemplate getCustomizedTemplate(Long userId, Long templateId) {
        return customizedTemplateRepository
                .findByTemplateIdAndUserIdAndStatus(
                        templateId,
                        userId,
                        "DRAFT"
                )
                .orElseThrow(() -> new IllegalArgumentException("커스터마이징된 임시 저장 템플릿이 없습니다."));
    }


    @Transactional
    public void confirmFinalCustomizedTemplate(Long userId, Long templateId) {
        CustomizedTemplate customizedTemplate = customizedTemplateRepository
                .findByTemplateIdAndUserIdAndStatus(templateId, userId, "DRAFT")
                .orElseThrow(() -> new IllegalArgumentException("확정할 임시 저장본이 없습니다."));

        customizedTemplate.setStatus("FINAL");  // DRAFT → FINAL
        customizedTemplate.setCreatedAt(LocalDateTime.now()); //수정 시간 업데이트

        customizedTemplateRepository.save(customizedTemplate);

        log.info("커스터마이징 최종 확정 완료: id = {}, title = {}", customizedTemplate.getId(), customizedTemplate.getCustomTitle());
    }

}


