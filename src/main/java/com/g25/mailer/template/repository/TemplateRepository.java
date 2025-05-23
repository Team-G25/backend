package com.g25.mailer.template.repository;

import com.g25.mailer.template.entity.Keyword;
import com.g25.mailer.template.entity.Target;
import com.g25.mailer.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    /**
     * 수정완료 version2
     * @param target
     * @param keyword1
     * @param keyword2
     * @return
     */
    @Query("SELECT t FROM Template t WHERE t.target = :target AND t.keyword1.keyword = :keyword1 AND t.keyword2.keyword = :keyword2")
    List<Template> findByTargetAndKeyword1AndKeyword2Equals(
            @Param("target") Target target,
            @Param("keyword1") String keyword1,
            @Param("keyword2") String keyword2
    );

    @Query("SELECT t FROM Template t WHERE t.target = :target AND t.keyword1.keyword = :keyword1 AND t.keyword2 IS NULL")
    List<Template> findByTargetAndKeyword1AndKeyword2IsNull(
            @Param("target") Target target,
            @Param("keyword1") String keyword1
    );



}



