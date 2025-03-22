package com.g25.mailer.user.service;

import com.g25.mailer.user.dto.SendTemplateRequest;
import com.g25.mailer.user.dto.TemplateRequest;
import com.g25.mailer.user.dto.TemplateResponse;
import com.g25.mailer.user.entity.Keyword;
import com.g25.mailer.user.entity.Target;
import com.g25.mailer.user.entity.Template;
import com.g25.mailer.user.repository.KeywordRepository;
import com.g25.mailer.user.repository.TargetRepository;
import com.g25.mailer.user.repository.TemplateRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
     * @param request
     * @throws MessagingException
     */
    public void sendEmailTemplate(SendTemplateRequest request) throws MessagingException {
        Template template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("조회되는 템플릿이 없습니다."));

        //수정
        String finalTitle = (request.getCustomTitle() != null && !request.getCustomTitle().isBlank())
                ? request.getCustomTitle()
                : template.getTitle(); // 없으면 기본제목 그대로

        String finalContent = (request.getCustomContent() != null && !request.getCustomContent().isBlank())
                ? request.getCustomContent()
                : template.getContent(); //기본내용 그대로

        emailService.sendMail(request.getRecipientEmail(), finalTitle, finalContent, request.getFrom());
    }

    public void saveEmailTemplate() {

    }

    /**
     * 임시저장
     */




}
