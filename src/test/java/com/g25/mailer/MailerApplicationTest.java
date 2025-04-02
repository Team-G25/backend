package com.g25.mailer;

import com.g25.mailer.email.service.EmailService;
import com.g25.mailer.user.repository.UserRepository;
import com.g25.mailer.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailerApplicationTest {

    private UserService userService;


    private EmailService emailService;

    private UserRepository userRepository;

    @Test
    void contextLoads() {
        // 컨텍스트 로딩 테스트
    }
}