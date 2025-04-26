package com.g25.mailer.template.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "customized_templates")
@Getter
@Setter
public class CustomizedTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long templateId;

    private String customTitle;

    @Lob
    private String customContent;

    private Long userId;

    private String status; // "DRAFT", "FINAL"

    private LocalDateTime createdAt = LocalDateTime.now();

    public CustomizedTemplate() {
    }
}
