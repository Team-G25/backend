package com.g25.mailer.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * 이메일을 전송하는 메서드
     * @param to 받는 사람 이메일
     * @param title 이메일 제목
     * @param content 이메일 내용
     */
    public void sendMail(String to, String title, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("메일이 성공적으로 전송되었습니다. 받는 사람: {}", to);
        } catch (MessagingException e) {
            log.error("메일 전송 실패: {}", e.getMessage());
            throw new RuntimeException("메일 전송 중 오류 발생");
        }
    }
}
