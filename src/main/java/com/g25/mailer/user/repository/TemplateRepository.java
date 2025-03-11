package com.g25.mailer.user.repository;

import com.g25.mailer.user.entity.Keyword;
import com.g25.mailer.user.entity.Target;
import com.g25.mailer.user.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByTargetAndKeyword1AndKeyword2(Target target, String keyword1, String keyword2);
}
