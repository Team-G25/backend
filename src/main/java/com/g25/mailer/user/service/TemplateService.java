package com.g25.mailer.user.service;

import com.g25.mailer.user.entity.Keyword;
import com.g25.mailer.user.entity.Target;
import com.g25.mailer.user.entity.Template;
import com.g25.mailer.user.repository.KeywordRepository;
import com.g25.mailer.user.repository.TargetRepository;
import com.g25.mailer.user.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final TargetRepository targetRepository;
    private final KeywordRepository keywordRepository;

    /**
     * 선택
     */
    public Optional<Template> getTemplate(String target, String keyword1, String keyword2) {
        Optional<Target> targetOpt = targetRepository.findTargetByTargetNameEquals(target);

        if (targetOpt.isPresent()) { //예외처리
            throw new NoSuchElementException();
        }
        Target tar = targetOpt.get();

        Optional<Keyword> keywordOpt = keywordRepository.findKeywordByKeywordEquals(keyword1);
        Optional<Keyword> subKeywordOpt = keywordRepository.findKeywordByKeywordEquals(keyword2);


        String key1;
        String key2;

        if (keywordOpt.isPresent() && subKeywordOpt.isPresent()) {
            key1 = String.valueOf(keywordOpt.get());
            key2 = String.valueOf(subKeywordOpt.get());

        } else {
            throw new NoSuchElementException();
        }

        return templateRepository.findByTargetAndKeyword1AndKeyword2(tar, key1, key2); //아직 다 못함.
    }
}
