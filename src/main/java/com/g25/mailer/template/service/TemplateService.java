package com.g25.mailer.template.service;

import com.g25.mailer.email.service.EmailService;
import com.g25.mailer.template.dto.SendTemplateRequest;
import com.g25.mailer.template.dto.TemplateResponse;
import com.g25.mailer.template.entity.Keyword;
import com.g25.mailer.template.entity.Target;
import com.g25.mailer.template.entity.Template;
import com.g25.mailer.template.repository.KeywordRepository;
import com.g25.mailer.template.repository.TargetRepository;
import com.g25.mailer.template.repository.TemplateRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 구현중입니다.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final KeywordRepository keywordRepository;
    private final TargetRepository targetRepository;

    private final EmailService emailService;

    /**
     * 템플릿 조회
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

        String key2 = (keyword2 != null && !keyword2.isEmpty())
                ? keywordRepository.findByKeyword(keyword2).map(Keyword::getKeyword).orElse(null)
                : null;

        List<Template> templates = templateRepository.findByTargetAndKeyword1AndKeyword2(target, key1.getKeyword(), key2);

        return templates.stream()
                .map(t -> new TemplateResponse(t.getTitle(), t.getContent(), t.getTarget(), t.getKeyword1(), t.getKeyword2()))
                .toList();
    }


    /**
     * 템플릿조회 + 사용자커스텀 + 이메일 송신
     *  1. 첨부파일유무에 따라 emailService에서 분기
     * @param request
     * @throws MessagingException
     */
    public void sendEmailTemplate(SendTemplateRequest request) throws MessagingException {
        Template template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("조회되는 템플릿이 없습니다."));

        //제목,내용 수정할 경우 변경 반영
        String finalTitle = (request.getCustomTitle() != null && !request.getCustomTitle().isBlank())
                ? request.getCustomTitle()
                : template.getTitle();

        String finalContent = (request.getCustomContent() != null && !request.getCustomContent().isBlank())
                ? request.getCustomContent()
                : template.getContent();

        // 첨부파일유뮤에 따라 있으면 fileKeys(파일명)로 초기화
        List<String> fileKeys = (request.getAttachmentKeys() != null) ? request.getAttachmentKeys() : List.of();

        if (fileKeys.isEmpty()) {
            emailService.sendSimpleMail(
                    request.getTo(),
                    finalTitle,
                    finalContent,
                    request.getFrom()
            );
        } else {
            emailService.sendMailWithAttachment(
                    request.getTo(),
                    finalTitle,
                    finalContent,
                    request.getFrom(),
                    fileKeys
            );
        }
    }







}
