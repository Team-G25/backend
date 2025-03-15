package com.g25.mailer.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 유저가 수정한 이메일 저장
 */
@Entity
@Table(name = "saved_emails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template; //사용한 템플리

    @Column(nullable = false)
    private String recipient; //수신자

    @Column(nullable = false)
    private String title; // 이메일 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; //템플릿에서 수정된 이메일

    @Column(nullable = false)
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        this.savedAt = LocalDateTime.now();
    }
}
