package com.g25.mailer.template.dto;

import com.g25.mailer.template.entity.Keyword;
import com.g25.mailer.template.entity.Target;
import com.g25.mailer.template.entity.Template;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Optional;


@Data
@Builder
@Schema(description = "템플릿 조회 응답")
public class TemplateResponse {
    private Long templateId; // 추가
    private final String title;
    private final String content;
    private final String targetName;
    private final String keyword1;
    private final String keyword2;

    // Template 엔티티 기반 생성자
    public TemplateResponse(Template template) {
        this.templateId = template.getId();
        this.title = template.getTitle();
        this.content = template.getContent();
        this.targetName = Optional.ofNullable(template.getTarget())
                .map(Target::getTargetName)
                .orElse("알 수 없음");

        this.keyword1 = Optional.ofNullable(template.getKeyword1())
                .map(Keyword::getKeyword)
                .orElse("없음");

        this.keyword2 = Optional.ofNullable(template.getKeyword2())
                .map(Keyword::getKeyword)
                .orElse("없음");
    }

    public TemplateResponse(Long templateId, String title, String content, String targetName, String keyword1, String keyword2) {
        this.templateId = templateId;
        this.title = title;
        this.content = content;
        this.targetName = targetName;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
    }


}
