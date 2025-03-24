package com.g25.mailer.template.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 미리 정의된 템플릿
 */
@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    @ToString.Exclude
    private Target target; //대상: 직장인, 학생

    @Column(nullable = false)
    private String title; //템플릿 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; //템플릿 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword1_id", nullable = false)
    @ToString.Exclude //순환참조 금지 어노테이션임.
    private Keyword keyword1; //주 키워드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword2_id", nullable = true)
    @ToString.Exclude
    private Keyword keyword2; //세부키워드
}
