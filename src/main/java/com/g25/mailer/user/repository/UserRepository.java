package com.g25.mailer.user.repository;

import com.g25.mailer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //사용자 id조회(email)
    Optional<User> findByEmail(String email); //email로 사용자 정보 가져옴
}


