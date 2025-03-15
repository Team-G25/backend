package com.g25.mailer.user.repository;

import com.g25.mailer.user.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
    Optional<Target> findByTargetName(String targetName);
}
