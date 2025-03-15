package com.g25.mailer.user.service;

import com.g25.mailer.user.dto.TemplateResponse;
import com.g25.mailer.user.entity.Keyword;
import com.g25.mailer.user.entity.Target;
import com.g25.mailer.user.entity.Template;
import com.g25.mailer.user.repository.KeywordRepository;
import com.g25.mailer.user.repository.TargetRepository;
import com.g25.mailer.user.repository.TemplateRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 구현중입니다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final KeywordRepository keywordRepository;
    private final TargetRepository targetRepository;

    private final EmailService emailService;


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



    //템플릿 메일 송신
    public void sendEmailTemplate(Long templateId, String recipientEmail, String customContent) throws MessagingException {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("No Template Found"));

        String finalContent = customContent != null ? customContent : template.getContent();
        emailService.sendMail(recipientEmail, template.getTitle(), finalContent);
    }
}
