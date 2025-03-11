package com.g25.mailer.user.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Target target;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword1_id", nullable = false)
    @ToString.Exclude //순환참조 금지 어노테이션임.
    private Keyword keyword1; //주 키워드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword2_id", nullable = false)
    @ToString.Exclude
    private Keyword keyword2; //세부키워드
}
