package com.g25.mailer.template.repository;

import com.g25.mailer.template.entity.CustomizedTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomizedTemplateRepository extends JpaRepository<CustomizedTemplate, Long> {

    Optional<CustomizedTemplate> findByTemplateIdAndUserIdAndStatus(Long templateId, Long userId, String status);
}
