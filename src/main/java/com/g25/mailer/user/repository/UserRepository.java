package com.g25.mailer.User.repository;

import com.g25.mailer.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByLoginId(String loginId);

    boolean existsById(Long id);

    Optional<User> findByLoginId(String loginId);

}
