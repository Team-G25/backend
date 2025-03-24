package com.g25.mailer.template.dto;

import com.g25.mailer.template.entity.Keyword;
import com.g25.mailer.template.entity.Target;
import lombok.Getter;

@Getter
public class TemplateResponse {
    private final String title;
    private final String content;
    private final String targetName;
    private final String keyword1;
    private final String keyword2;

    public TemplateResponse(String title, String content, Target target, Keyword keyword1, Keyword keyword2) {
        this.title = title;
        this.content = content;
        this.targetName = target.getTargetName();
        this.keyword1 = keyword1.getKeyword();
        this.keyword2 = (keyword2 != null) ? keyword2.getKeyword() : "없음";
    }
}
